package com.ts.Service.Impl;

import com.ts.Entity.ExceptionLog;
import com.ts.Entity.OperationLog;
import com.ts.Entity.Result;
import com.ts.Mapper.ExceptionLogMapper;
import com.ts.Mapper.OperationLogMapper;
import com.ts.Service.IExceptionLogService;
import com.ts.Service.IOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Objects;

import static com.ts.Constants.RedisConstants.PV_CACHE_KEY;
import static com.ts.Constants.RedisConstants.UV_CACHE_KEY;

@Service
@Slf4j
public class LogService implements IOperationLogService, IExceptionLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Async
    public void logRequest(JoinPoint joinPoint, String method, String ip, String classMethod, String userAgent, String adminName) {
        redisTemplate.opsForValue().increment(PV_CACHE_KEY);
        //记录管理员操作日志
        if(method!=null&&ip!=null&&!Objects.equals(method, "GET")&&!classMethod.equals("deleteOperationLogById")&&!classMethod.equals("login")){
            logAdminRequest(method, ip, classMethod, joinPoint.getArgs(), adminName, userAgent);
        }
        //TODO: 记录访问者日志
        if (ip != null) {
            redisTemplate.opsForSet().add(UV_CACHE_KEY, ip);
            logVisitor(ip, userAgent);
        }
        Object[] args = joinPoint.getArgs();
        log.info("Request: method={} ip={} classMethod={} args={}", method, ip, classMethod, args);
    }


    @Async
    public void logException(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        String errorMessage = error.getMessage();
        String errorType = error.getClass().getName();
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setMethodName(methodName);
        exceptionLog.setErrorMessage(errorMessage);
        exceptionLog.setErrorType(errorType);
        exceptionLog.setCreateTime(LocalDateTime.now());
        saveExceptionLog(exceptionLog);
    }

    private void logVisitor(String ip, String userAgent) {

    }

    private void logAdminRequest(String method, String ip, String classMethod, Object[] args, String adminName, String userAgent) {
        OperationLog operationLog = new OperationLog();
        operationLog.setIp(ip);
        operationLog.setMethodName(classMethod);
        operationLog.setOperationName(method);
        operationLog.setOperationTime(LocalDateTime.now());
        operationLog.setUserName(adminName);
        operationLog.setUserAgent(userAgent);
        saveOperationLog(operationLog);
    }

    @Override
    public Result saveExceptionLog(ExceptionLog exceptionLog) {
        boolean success=exceptionLogMapper.insert(exceptionLog)>0;
        if(success) return Result.success();
        else return Result.error("保存日志失败");
    }

    @Override
    public Result getExceptionLogList() {
        return Result.success(exceptionLogMapper.selectList(null));
    }

    @Override
    public Result deleteExceptionLog(Integer id) {
        boolean success=exceptionLogMapper.deleteById(id)>0;
        if(success) return Result.success();
        else return Result.error("删除日志失败");
    }

    @Override
    public Result getOperationLog() {
        return Result.success(operationLogMapper.selectList(null));
    }

    @Override
    public Result deleteOperationLog(Integer id) {
        boolean success=operationLogMapper.deleteById(id)>0;
        if(success) return Result.success();
        else return Result.error("删除日志失败");
    }

    @Override
    public Result saveOperationLog(OperationLog operationLog) {
        boolean success=operationLogMapper.insert(operationLog)>0;
        if(success) return Result.success();
        else return Result.error("保存日志失败");
    }
}
