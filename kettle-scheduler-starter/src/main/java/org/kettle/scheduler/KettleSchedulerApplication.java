package org.kettle.scheduler;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.kettle.scheduler.common.utils.IpUtil;
import org.kettle.scheduler.core.init.EnableEtlKettle;
import org.mybatis.spring.annotation.MapperScan;
import org.pentaho.di.core.annotations.CarteServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 启动类
 *
 * {@code @EnableJpaAuditing} 该注解可以使得jpa自动生成创建时间和修改时间
 * {@code @EnableEtlKettle} 该注解初始化kettle
 * @author lyf
 */
@EnableSwagger2Doc
@SpringBootApplication
@EnableJpaAuditing
@EnableEtlKettle
@EnableScheduling
@CarteServlet(id = "test", name = "test")
@MapperScan("org.kettle.scheduler.system.biz.mapper")
public class KettleSchedulerApplication {

    public static void main(String[] args) {

        // 获取 Spring Boot 上下文
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KettleSchedulerApplication.class, args);
        Environment env = applicationContext.getEnvironment();

        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");

        String ip = IpUtil.getIp();
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Application kettle-scheduler-boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port + path + "swagger-ui.html\n\t" +
                "----------------------------------------------------------");
    }
}
