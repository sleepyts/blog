package com.ts.Aspect;

import com.ts.Service.Impl.LogService;
import com.ts.Utils.Holder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class Log {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.ts.Annotation.RequestLog)")
    public void requestLog() {

    }
    @Around("requestLog()")
    public Object logBefore(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();

        try {
            Object proceed = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logService.logRequest(Holder.getCurrentVisitor(), Holder.getAdmin(), joinPoint, end - start);
            return proceed;
        } catch (Throwable e) {
            log.error("error", e);
        }
        return null;
    }

    // 异常通知：在目标方法抛出异常后执行
    @AfterThrowing(pointcut = "execution(* com.ts.Controller..*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logService.logException(joinPoint, error);
    }
}
