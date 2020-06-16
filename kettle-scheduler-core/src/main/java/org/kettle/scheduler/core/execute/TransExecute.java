package org.kettle.scheduler.core.execute;

import lombok.extern.slf4j.Slf4j;
import org.kettle.scheduler.common.utils.CollectionUtil;
import org.kettle.scheduler.common.utils.FileUtil;
import org.kettle.scheduler.common.utils.JsonUtil;
import org.kettle.scheduler.core.log.KettleLogUtil;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.parameters.UnknownParamException;
import org.pentaho.di.repository.AbstractRepository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

import java.util.*;

/**
 * kettle的ktr脚本执行器
 *
 * @author lyf
 */
@Slf4j
public class TransExecute {

    /**
     * ktr执行单元，所有的ktr执行都调用该方法
     *
     * @param tm     ktr元数据
     * @param params ktr需要的命名参数
     * @param args   命令行参数
     */
    private static String executeTrans(TransMeta tm, Map<String, String> params, String[] args, LogLevel logLevel) throws KettleException {

        /**
         * 同一脚本，同时运行时，日志会串。
         * 原因：
         * LogChannelID是根据transName和stepName来获取的，便于处理
         * 在脚本和步骤名称中加入随机数，保证名称不冲突问题，计算出来的logChannelId不会出现重复的情况
         */
        Random random = new Random();
        int i = random.nextInt(1000);
        //设置变动的stepName
        List<StepMeta> steps = tm.getSteps();
        steps.forEach(stepMeta -> {
            stepMeta.setName(stepMeta.getName() + random.nextInt(1000));
        });
        // 通过元数据获取ktr的实例
        Trans trans = new Trans(tm);
        LogChannel logChannel = new LogChannel(trans.getName() + i + System.currentTimeMillis());
        trans.setLog(logChannel);
        if (logLevel != null) {
            trans.setLogLevel(logLevel);
        }
        // 传入ktr需要的变量
        if (CollectionUtil.isNotEmpty(params)) {
            log.info("传入kettle的参数：{}", JsonUtil.toJsonString(params));
            params.forEach((String k, Object v) -> {
                try {
                    trans.setParameterValue(k, v.toString());
                } catch (UnknownParamException e) {
                    e.printStackTrace();
                }
            });
            /*for (Map.Entry<String, String> entry : params.entrySet()) {
                trans.setParameterValue(entry.getKey(), entry.getValue());
            }*/
        }
        // 开始执行ktr，该方法以传入命令行参数args

        trans.execute(args);
        // 线程等待，直到ktr执行完成
        trans.waitUntilFinished();
        // 执行完成后获取日志

        String logText = KettleLogUtil.getLogText(trans.getLogChannelId(), true, trans.getLogDate().getTime());


        // 判断执行过程中是否有错误, 有错误就抛出错误日志
        if (trans.getErrors() > 0) {
            throw new KettleException(logText);
        }
        // 没有错误就返回正常执行日志
        return logText;
    }

    /**
     * 运行单个ktr
     *
     * @param fullPathName ktr全路径名
     * @param params       ktr需要的命名参数
     * @param logLevel     日志级别
     */
    public static String run(String fullPathName, Map<String, String> params, LogLevel logLevel) throws KettleException {
        // 通过ktr全路径名获取ktr元数据
        TransMeta tm = new TransMeta(FileUtil.replaceSeparator(fullPathName));
        // 开始执行ktr
        return executeTrans(tm, params, null, logLevel);
    }

    /**
     * 运行资源库中的ktr
     *
     * @param rep          资源库对象
     * @param transPath    ktr所在路径
     * @param transName    ktr名称
     * @param versionLabel 版本号，传入null表示执行最新的ktr
     * @param params       ktr需要的命名参数
     * @param logLevel     日志级别
     */
    public static String run(AbstractRepository rep, String transPath, String transName, String versionLabel, Map<String, String> params, LogLevel logLevel) throws KettleException {
        // 根据相对目录地址获取ktr所在目录信息
        RepositoryDirectoryInterface rdi = rep.loadRepositoryDirectoryTree().findDirectory(FileUtil.getParentPath(transPath));
        // 在指定资源库的目录下找到要执行的转换
        TransMeta tm = rep.loadTransformation(transName, rdi, new ProgressNullMonitorListener(), true, versionLabel);
        // 开始执行ktr
        return executeTrans(tm, params, null, logLevel);
    }
}
