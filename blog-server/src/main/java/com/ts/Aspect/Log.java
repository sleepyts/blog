package com.ts.Aspect;

import com.ts.Entity.Admin;
import com.ts.Service.Impl.LogService;
import com.ts.Utils.AdminHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static com.ts.Utils.ipAddressUtils.getClientIp;

@Aspect
@Component
@Slf4j
public class Log {

    @Autowired
    private LogService logService;


    @Pointcut("@annotation(com.ts.Annotation.RequestLog)")
    public void requestLog() {

    }


    // 前置通知：在目标方法调用前执行
    @Before(value = "requestLog()")
    public void logBefore(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) return;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Admin admin = AdminHolder.getAdmin();
        String method = request.getMethod();
        String ip = getClientIp(request.getHeader("X-Forwarded-For"),request.getHeader("X-Real-IP"),ip = request.getRemoteAddr());
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String classMethod=methodName.substring(methodName.lastIndexOf(".") + 1);
        String userAgent = request.getHeader("User-Agent");
        logService.logRequest(joinPoint, method, ip, classMethod, userAgent, admin==null?null:admin.getUsername());
    }


    // 异常通知：在目标方法抛出异常后执行
    @AfterThrowing(pointcut = "execution(* com.ts.Controller..*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logService.logException(joinPoint, error);
    }
}
