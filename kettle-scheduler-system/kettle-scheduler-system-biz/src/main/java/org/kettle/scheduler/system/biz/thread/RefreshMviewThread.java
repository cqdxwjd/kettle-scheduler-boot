package org.kettle.scheduler.system.biz.thread;

import org.kettle.scheduler.system.biz.service.SysMviewService;
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
public class RefreshMviewThread {

    @Autowired
    SysMviewService sysMviewService;

    /**
     * 批量刷新物化视图
     *
     * @param mviewTag
     */
    @Async("refreshMviewTaskExecutor")
    public void refreshMview(String keyword) {
        sysMviewService.refreshMview(keyword,new String());
    }
}
