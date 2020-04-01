package org.kettle.scheduler.system.biz.thread;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.kettle.scheduler.system.api.entity.Ftp;
import org.kettle.scheduler.system.biz.entity.FtpFile;
import org.kettle.scheduler.system.biz.service.SysMviewService;
import org.kettle.scheduler.system.biz.util.FtpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-26 15:27
 */
@Component
public class DownloadFtpFileThread {

    @Autowired
    SysMviewService sysMviewService;

    /**
     * 异步下载文件
     *
     * @param
     */
    @Async("downloadFtpFileExecutor")
    @XxlJob(value = "downloadFtpFileJob",remark = "FTP下载文件")
    public ReturnT<String> run(String param) throws IOException {
        Ftp ftp = new Ftp();
        FtpClientUtil ftpCli = FtpClientUtil.createFtpCli(ftp.getHost(), ftp.getPort(), ftp.getUsername(), ftp.getPassword(), ftp.getCharset(), ftp.getLocalFilePath());
        ftpCli.connect();
        ftpCli.setBatchNo("PAY_" + String.valueOf(System.currentTimeMillis()));
        /**
         * 1、生成批次号，将当前批次号所有区划信息取出来
         * 2、区划信息写入redis，将所有区划导入状态设置未未导入
         * 3、导入完成后，更新相应区划下的导入状态
         * 4、更新完成后，检查一下当前区划是否全部完成
         * 5、全部完成后，发送MQ消息，调用当前区划转换任务
         */
        List<FtpFile> fileList = new ArrayList<>();
        fileList = ftpCli.listFileNames(ftp.getDir(), fileList);
        return ReturnT.SUCCESS;
    }

    @XxlJob(value = "testXxlJob",remark = "测试方法")
    public ReturnT<String> execure(String param) {
        return ReturnT.SUCCESS;
    }
}
