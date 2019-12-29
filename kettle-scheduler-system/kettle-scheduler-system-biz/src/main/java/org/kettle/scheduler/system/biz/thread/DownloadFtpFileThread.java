package org.kettle.scheduler.system.biz.thread;

import org.kettle.scheduler.system.biz.service.SysMviewService;
import org.kettle.scheduler.system.biz.util.FtpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
    public void refreshMview(String keyword, FtpClientUtil ftpClientUtil) {
        //ftpClientUtil.download();
    }
}
