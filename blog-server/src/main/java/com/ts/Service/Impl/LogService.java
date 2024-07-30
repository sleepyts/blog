package com.ts.Service.Impl;

import com.ts.Entity.ExceptionLog;
import com.ts.Entity.OperationLog;
import com.ts.Entity.Result;
import com.ts.Entity.Visitor;
import com.ts.Mapper.ExceptionLogMapper;
import com.ts.Mapper.OperationLogMapper;
import com.ts.Mapper.VisitorMapper;
import com.ts.Service.IExceptionLogService;
import com.ts.Service.IOperationLogService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ts.Constants.RedisConstants.PV_CACHE_KEY;
import static com.ts.Constants.RedisConstants.UV_CACHE_KEY;
import static com.ts.Utils.ipAddressUtils.getCityInfo;

@Service
@Slf4j
public class LogService implements IOperationLogService, IExceptionLogService {

    @Value("${config.log.path}")
    private String logPath;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Async
    public void logRequest(JoinPoint joinPoint, String method, String ip, String classMethod, String userAgent, String adminName) {
        redisTemplate.opsForValue().increment(PV_CACHE_KEY);
        //记录管理员操作日志
        if(method!=null&&ip!=null&&!Objects.equals(method, "GET")&&!classMethod.equals("deleteOperationLogById")&&!classMethod.equals("login")){
            logAdminRequest(method, ip, classMethod, joinPoint.getArgs(), adminName, userAgent);
        }
        //记录访客日志
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
        Boolean isNewVisitor=!visitorMapper.isContains(ip);
        if(Boolean.TRUE.equals(isNewVisitor)){
            Visitor visitor=new Visitor();
            visitor.setIp(ip);
            visitor.setUserAgent(userAgent);
            visitor.setFirstVisitTime(LocalDateTime.now());
            visitor.setLastVisitTime(LocalDateTime.now());
            visitor.setAddress(getCityInfo(ip));
            visitorMapper.insert(visitor);
        }
        else{
            Visitor visitor=visitorMapper.selectByIp(ip);
            visitor.setLastVisitTime(LocalDateTime.now());
            visitorMapper.updateLastVisit(visitor);
        }
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
    public Result getExceptionLogList(LocalDateTime startTime, LocalDateTime endTime) {
        startTime=startTime==null?LocalDateTime.of(2005,7,24,0,0):startTime;
        endTime=endTime==null?LocalDateTime.now():endTime;
        return Result.success(exceptionLogMapper.selectByTime(startTime, endTime));
    }

    @Override
    public Result deleteExceptionLog(Integer id) {
        boolean success=exceptionLogMapper.deleteById(id)>0;
        if(success) return Result.success();
        else return Result.error("删除日志失败");
    }

    @Override
    public Result getOperationLog(LocalDateTime startTime, LocalDateTime endTime) {
        startTime=startTime==null?LocalDateTime.of(2005,7,24,0,0):startTime;
        endTime=endTime==null?LocalDateTime.now():endTime;
        return Result.success(operationLogMapper.selectByTime(startTime, endTime));
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

    public Result getLogFile(){
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(logPath))) {
            contentBuilder.append(stream.collect(Collectors.joining("\n")));
        } catch (IOException e) { 
            e.printStackTrace();
            return Result.error("获取日志失败");
        }

        return Result.success(contentBuilder.toString());
    }
}
