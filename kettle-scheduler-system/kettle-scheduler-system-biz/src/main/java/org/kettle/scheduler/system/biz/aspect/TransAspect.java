package org.kettle.scheduler.system.biz.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransAspect {
    //定义切点
    @Pointcut("execution(* org.quartz.Job.execute(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object roundAsp(ProceedingJoinPoint pj) {
        System.out.println("前");
        Object proceed = null;;

        return proceed;

    }


}
