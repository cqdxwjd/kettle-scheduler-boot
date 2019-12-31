package org.kettle.scheduler.system.biz.thread;

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
    public void run(Ftp ftp) throws IOException {
        FtpClientUtil ftpCli = FtpClientUtil.createFtpCli(ftp.getHost(), ftp.getPort(), ftp.getUsername(), ftp.getPassword(), ftp.getCharset(), ftp.getLocalFilePath());
        ftpCli.connect();
        List<FtpFile> fileList = new ArrayList<>();
        fileList = ftpCli.listFileNames(ftp.getDir(), fileList);
    }
}
