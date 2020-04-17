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
     * @param keyword   查询关键词
     * @param type      刷新类别，1通过K_MVIEW表获取物化视图，0直接从源库获取
     */
    @Async("refreshMviewTaskExecutor")
    public void refreshMview(String keyword, String type) {
        sysMviewService.refreshMview(keyword, type, new String());
    }
}
