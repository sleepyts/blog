package com.ts.Aspect;

import com.ts.Annotation.Cacheable;
import com.ts.Model.Entity.Result;
import com.ts.Service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2) // 保证该切面在记录日志的切面之后执行
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.ts.Annotation.Cacheable)")
    public void cacheablePointcut() {
        // 切入点
    }

    @Around("cacheablePointcut()")
    public Result cacheableAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Cacheable cacheable = signature.getMethod().getAnnotation(Cacheable.class);
        Object[] args = joinPoint.getArgs();

        String key = cacheable.KEY();
        if (args != null) {
            for (Object arg : args)
                key += ":" + arg;
        }
        // 从缓存中获取数据
        Object cachedData = redisService.get(key);
        if (cachedData != null) {
            return Result.success(cachedData);
        }

        try {
            Result result = (Result) joinPoint.proceed();
            if (result != null) {
                redisService.set(key, result.getData());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }

    }
}
