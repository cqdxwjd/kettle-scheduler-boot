package org.kettle.scheduler.system.biz.configuration;

import com.xxl.job.core.biz.client.AdminBizClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description
 * @auther chen1
 * @create 2020-01-06 17:31
 */

@Configuration
public class XxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Autowired
    private ApplicationContext applicationContext;
    //@Autowired
    //ConcurrentMap<String, IJobHandler> jobHandlerRepository;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppName(appName);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobSpringExecutor;
    }

    /**
     * 自动向服务器注册当前的Handler
     */
    //@Bean
    //@Order(10)
    @Scheduled(cron = "0/60 * * * * ?")
    public void registeMethod() {
        //logger.info("定时注册");
        ConcurrentMap<String, IJobHandler> jobHandlerRepository = XxlJobExecutor.getJobHandlerRepository();
        Map param = new HashMap();
        AdminBizClient adminBizClient = new AdminBizClient(adminAddresses, accessToken);
        jobHandlerRepository.forEach((k, v) -> {
            //System.out.println(k + v);
            Method method = ((MethodJobHandler) v).getMethod();
            XxlJob xxlJob = AnnotationUtils.findAnnotation(method, XxlJob.class);
            Map map = new HashedMap();
            map.put("methodName", xxlJob.value());
            map.put("remark", xxlJob.remark());
            map.put("appName", appName);
            param.put(xxlJob.value(), map);
            //System.out.println(returnT.getCode());
        });
        try {
            ReturnT returnT = adminBizClient.registryMethod(param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
