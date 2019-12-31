package org.kettle.scheduler.system.biz.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 描述:
 * 异步线程池
 *
 * @author leo
 * @create 2019-12-26 15:23
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    //刷新物化视图线程池配置
    @Value("${thread.refreshMview.corePoolSize}")
    private int refreshMviewCorePoolSize;
    @Value("${thread.refreshMview.maxPoolSize}")
    private int refreshMviewMaxPoolSize;
    @Value("${thread.refreshMview.queueCapacity}")
    private int refreshMviewQueueCapacity;
    @Value("${thread.refreshMview.keepAliveSeconds}")
    private int refreshMviewKeepAliveSeconds;

    //文件下载线程池配置
    @Value("${thread.downloadFtpFile.corePoolSize}")
    private int downloadFtpFileCorePoolSize;
    @Value("${thread.downloadFtpFile.maxPoolSize}")
    private int downloadFtpFileMaxPoolSize;
    @Value("${thread.downloadFtpFile.queueCapacity}")
    private int downloadFtpFileQueueCapacity;
    @Value("${thread.downloadFtpFile.keepAliveSeconds}")
    private int downloadFtpFileKeepAliveSeconds;

    //导入数据库线程池配置
    @Value("${thread.implDb.corePoolSize}")
    private int implDbCorePoolSize;
    @Value("${thread.implDb.maxPoolSize}")
    private int implDbMaxPoolSize;
    @Value("${thread.implDb.queueCapacity}")
    private int implDbQueueCapacity;
    @Value("${thread.implDb.keepAliveSeconds}")
    private int implDbKeepAliveSeconds;

    // 声明一个线程池(并指定线程池的名字)
    @Bean("refreshMviewTaskExecutor")
    public Executor refreshMviewTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(refreshMviewCorePoolSize);
        //最大线程数50：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(refreshMviewMaxPoolSize);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(refreshMviewQueueCapacity);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(refreshMviewKeepAliveSeconds);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("RefreshMviewAsync-");
        executor.initialize();
        return executor;
    }

    // 声明一个线程池(并指定线程池的名字)
    @Bean("downloadFtpFileExecutor")
    public Executor downloadFtpFileExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(downloadFtpFileCorePoolSize);
        //最大线程数50：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(downloadFtpFileMaxPoolSize);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(downloadFtpFileQueueCapacity);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(downloadFtpFileKeepAliveSeconds);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("downloadFtpFileAsync-");
        executor.initialize();
        return executor;
    }


    // 声明一个线程池(并指定线程池的名字)
    @Bean("implDbExecutor")
    public Executor implDbThread() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(implDbCorePoolSize);
        //最大线程数50：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(implDbMaxPoolSize);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(implDbQueueCapacity);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(implDbKeepAliveSeconds);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("implDbExecutorAsync-");
        executor.initialize();
        return executor;
    }
}
