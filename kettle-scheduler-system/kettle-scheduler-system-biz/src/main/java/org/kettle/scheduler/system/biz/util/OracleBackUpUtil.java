package org.kettle.scheduler.system.biz.util;

import org.kettle.scheduler.common.utils.SpringContextUtil;
import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.kettle.scheduler.system.biz.service.DataSourceUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 00:21
 */
public class OracleBackUpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OracleBackUpUtil.class);

    public static void main(String[] args) {
//数据库的名称要使用大写
        backUpDataBaseOracle("cs", "hdx", "ORCL", "D:/oralce");
//resumeDataBaseOracle("cs", "hdx", "ORCL", "D:/oralce");
    }

    /**
     *   * 备份指定用户数据库
     *   * @param userName
     *   * 用户名
     *   * @param password
     *   * 密码
     *   * @param databseName
     *   * 数据库名称
     *   * @param address
     *   * 保存地址 d:/oracle/backup
     *  
     */

    private static void backUpDataBaseOracle(String userName, String password, String databseName, String address) {
//拼接dos命令进行数据库备份
        StringBuffer exp = new StringBuffer("exp ");
        exp.append(userName);
        exp.append("/");
        exp.append(password);
        exp.append("@");
        exp.append(databseName);
        exp.append(" file=");
/*
   * 得到存储地址的最后一个字符，如果有“\”就直接拼装地址，如果没有\就加上/然后拼装数据库名称
   * */
        String maxIndex = address.substring(address.length() - 1);
        if ("/".equals(maxIndex) || "\\".equals(maxIndex)) {
            exp.append(address);
        } else {
            address = address + "\\";
            exp.append(address);
        }
        File file = new File(address);
//如果文件不存在那么就重新创建，包括父目录
        if (!file.exists()) {
            file.mkdirs();
        }
        exp.append(databseName);
        exp.append(".dmp");
        System.out.println("开始备份");
        try {
            System.out.println(exp.toString());
            Process p = Runtime.getRuntime().exec(exp.toString());
            InputStreamReader isr = new InputStreamReader(p.getErrorStream());
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("错误") != -1) {
                    break;
                }

            }
            p.destroy();
            p.waitFor();
            System.out.println("备份成功！");
        } catch (IOException e) {
            System.out.println(e.getMessage());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //还原指定用户数据库
    public static void resumeDataBaseOracle(DatasourceUser datasourceUser, String address) {
        String errorMessage = "";
        //拼接DOS命令进行数据库还原
        StringBuffer imp = new StringBuffer("imp ");
        imp.append(datasourceUser.getUsername());
        imp.append("/");
        imp.append(datasourceUser.getPassword());
        imp.append("@");
        imp.append(datasourceUser.getDatabaseHost());
        imp.append(":");
        imp.append(datasourceUser.getDatabasePort());
        imp.append("/");
        imp.append(datasourceUser.getDatabaseName());
        imp.append(" file=");
        String maxIndex = address.substring(address.length() - 1);
        if ("/".equals(maxIndex) || "||".equals(maxIndex)) {
            imp.append(address);
        } else {
            //address = address + "\\";
            imp.append(address);
        }
        imp.append(" log=" + address + ".log");
        imp.append(" full=y ignore=y;");
        //imp.append(".dmp");
        File file = new File(address);
//判断文件是否存在，存在才进行恢复，不存在就不恢复
        if (file.exists()) {
            logger.info("执行导入语句：" + imp.toString());
            try {
                Process p = Runtime.getRuntime().exec(imp.toString());
                InputStreamReader isr = new InputStreamReader(p.getErrorStream(), "GBK");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder implLog = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    implLog.append(line);
                    //logger.info(line);/*|| line.indexOf("IMP") ! 33= -1*/
                    if (line.indexOf("错误") != -1) {
                        errorMessage = "导入失败，区划：" + datasourceUser.getAdmdivcode() + "；账号：" + datasourceUser.getUsername() + "；错误信息：" + line;
                        logger.error(errorMessage);
                        break;
                    } else if (line.indexOf("成功中止导入") != -1) {

                    }
                }
                p.destroy();
                if (errorMessage.length() == 0) {
                    logger.info("导入成功，区划：" + datasourceUser.getAdmdivcode() + "；账号：" + datasourceUser.getUsername());
                    if (new File(address).delete()) {
                        logger.info(file.getName() + " 文件已被删除！");
                    } else {
                        logger.info(file.getName() + "文件删除失败！");
                    }
                    DataSourceUserService dataSourceUserService = SpringContextUtil.getBean(DataSourceUserService.class);
                    datasourceUser.setLastImplDate(new Date());
                    dataSourceUserService.updateDatasourceUser(datasourceUser);
                }
                p.waitFor();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
