package org.kettle.scheduler.system.biz.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.kettle.scheduler.core.constant.KettleConfig;
import org.kettle.scheduler.core.init.KettleInit;
import org.kettle.scheduler.system.api.response.TaskCountRes;
import org.kettle.scheduler.system.biz.entity.TransRecord;
import org.kettle.scheduler.system.biz.service.SysTransMonitorService;
import org.kettle.scheduler.system.biz.service.SysTransService;
import org.kettle.scheduler.system.biz.util.HttpAsyncUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
@Log4j2
public class TransAspect {
    //定义切点
    @Pointcut("execution(* org.kettle.scheduler.system.biz.service.SysTransMonitorService.addTransRecord(..))")
    public void pointCut() {
    }

    @Autowired
    SysTransService sysTransService;

    @Autowired
    SysTransMonitorService monitorService;

    @Before("pointCut()")
    public void afterRefreshMview(JoinPoint joinPoint) {
        log.info("检查物化视图刷新");
        Object[] args = joinPoint.getArgs();
        TransRecord transRecords = (TransRecord) args[0];
        Integer categoryId = transRecords.getCategoryId();
        if (categoryId != null) {
            //查询分类下转换数量
            Integer size = sysTransService.countByCategoryIdAndstatus(categoryId);
            //查询该分类下今天执行数量
            TaskCountRes taskCountRes = monitorService.countTransByToday(categoryId);
            if (taskCountRes.getTotal() >= size) {
                String api = KettleConfig.vmUrl + categoryId;
                try {
                    String requestPath = KettleConfig.vmUrl + categoryId+"&type="+ KettleConfig.refreshType;
                    HttpAsyncUtils.get(requestPath);
                    log.info("刷新物化视图" + categoryId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
