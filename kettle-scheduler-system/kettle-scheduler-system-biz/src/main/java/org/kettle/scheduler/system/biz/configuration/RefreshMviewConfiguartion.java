package org.kettle.scheduler.system.biz.configuration;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.nio.client.HttpAsyncClient;
import org.kettle.scheduler.system.biz.util.HttpAsyncUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Log4j2
public class RefreshMviewConfiguartion {

    @Value("${refreshMviewApi}")
    String url;
    @Value("${cleanPageCacheApi}")
    String cleanPageCacheApi;

    //3.添加定时任务
    @Scheduled(cron = "0 0 4 * * ? ")
    //@Scheduled(fixedRate=5000)
    //@Scheduled(cron = "0 05 * * * ? ")
    private void refreshMviewTasks() throws ExecutionException, InterruptedException {
        String edwUrl = url + "?charCterName=edw_db&dbLink=edw_db_link&type=0";
        String dcEdwUrl = url + "?charCterName=zczl_sgjf_2020&dbLink=dc_edw2020_link&type=0";
        HttpAsyncUtils httpAsyncUtils = new HttpAsyncUtils();
        HttpAsyncUtils.get(edwUrl);
        HttpAsyncUtils.get(dcEdwUrl);
        //Future<HttpResponse> httpResponseFuture = HttpAsyncUtils.get("http://www.baidu.com");
        log.info("执行物化视图刷新，时间：" + LocalDateTime.now());
    }

    /**
     * 每天5点清理页面缓存
     */
    @Scheduled(cron = "0 0 5 * * ?")
    private void cleanPageCache() {
        HttpAsyncUtils httpAsyncUtils = new HttpAsyncUtils();
        HttpAsyncUtils.get(cleanPageCacheApi);
        log.info("清理页面缓存，时间：" + LocalDateTime.now());
    }
}
