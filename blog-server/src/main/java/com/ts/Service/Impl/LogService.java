package com.ts.Service.Impl;

import com.ts.Mapper.ExceptionLogMapper;
import com.ts.Mapper.OperationLogMapper;
import com.ts.Mapper.VisitorMapper;
import com.ts.Model.Entity.*;
import com.ts.Mq.OperationLogProducer;
import com.ts.Service.IExceptionLogService;
import com.ts.Service.IOperationLogService;
import com.ts.Utils.BloomFilters;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Autowired
    private BloomFilters bloomFilters;

    @Async
    public void logRequest(Visitor visitor, Admin admin, JoinPoint joinPoint, long requestTime) {
        // 记录请求时间
        long logStartTime=System.currentTimeMillis();

        // 日志策略
        enum LogStrategy {
            Admin {
                @Override
                public void log(String argsAndValue, Visitor visitor, Admin admin, JoinPoint joinPoint,
                        long requestTime ,long logTime) {
                    log.info("\nCurrent admin:{}\nMethod:{}\nArgs:{}\nTime:{}ms\nLogTime:{}ms\n",
                            admin.getUsername(),
                            joinPoint.getSignature().getName(),
                            argsAndValue,
                            requestTime,
                            logTime
                    );
                }
            },
            Visitor {
                @Override
                public void log(String argsAndValue, Visitor visitor, Admin admin, JoinPoint joinPoint,
                        long requestTime,long logTime) {
                    TaskLog taskLog=new TaskLog(
                            null,
                            visitor.getIp(),
                            visitor.getAddress(),
                            joinPoint.getSignature().getName(),
                            argsAndValue,
                            getOSInfo(visitor.getUserAgent()),
                            getBrowserInfo(visitor.getUserAgent()),
                            (int)requestTime,
                            null
                    );
                    OperationLogProducer.sendMessage(taskLog);
                }
            };

            public void log(String argsAndValue, com.ts.Model.Entity.Visitor visitor, com.ts.Model.Entity.Admin admin,
                    JoinPoint joinPoint, long requestTime,long logTime) {
                log.error("无效策略");
            }
        }

        // 拼装参数
        String argsNameAndValue = "";
        Parameter[] parameters;
        Object[] args = joinPoint.getArgs();
        try {
            Method methods = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(),
                    ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes());
            parameters = methods.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                argsNameAndValue += parameters[i].getName() + ":" + args[i] + ",";
            }
        } catch (Exception e) {
            log.error("获取方法失败:{}", e.getMessage());
            return;
        }


        // 管理员日志记录
        if (admin != null
                && !Objects.equals(joinPoint.getSignature().getName(), "login")
                && !Objects.equals(joinPoint.getSignature().getName(), "deleteOperationLogById")) {
            LogStrategy.Admin.log(argsNameAndValue, visitor, admin, joinPoint, requestTime, System.currentTimeMillis()-logStartTime);
            return;
        }
        // 访客日志记录
        if (visitor != null) {
            String ip = visitor.getIp();
            // 判断当日新访客，更新当日UVff
            if (!bloomFilters.dailyContain(ip)) {
                bloomFilters.dailyAdd(ip);

                // 增加UV
                redisTemplate.opsForHyperLogLog().add(UV_CACHE_KEY,ip);
            }
            // 判断是否已经存在该访客
            if (!bloomFilters.databaseContain(ip)) {
                visitor.setIp(ip);
                visitor.setFirstVisitTime(LocalDateTime.now());
                visitor.setAddress(getCityInfo(ip));
                visitorMapper.insert(visitor);
                bloomFilters.databaseAdd(ip);
            }
            visitor = visitorMapper.selectByIp(ip);
            visitor.setLastVisitTime(LocalDateTime.now());

            // 增加pv
            redisTemplate.opsForValue().increment(PV_CACHE_KEY, 1);
            visitorMapper.updateLastVisit(visitor);

            LogStrategy.Visitor.log(argsNameAndValue, visitor, admin, joinPoint, requestTime, System.currentTimeMillis()-logStartTime);
        }

    }
    public static String getBrowserInfo(String userAgent) {
        // 定义一个正则表达式模式
        String chromePattern = "Chrome/(\\d+\\.\\d+\\.\\d+\\.\\d+)";
        String firefoxPattern = "Firefox/(\\d+\\.\\d+)";
        String safariPattern = "Version/(\\d+\\.\\d+) Safari/";
        String edgePattern = "Edg/(\\d+\\.\\d+\\.\\d+)";
        String iePattern = "MSIE (\\d+\\.\\d+)|(Trident.*rv:(\\d+\\.\\d+))";

        String browserInfo = "Unknown Browser";

        // 检查 Chrome
        Matcher chromeMatcher = Pattern.compile(chromePattern).matcher(userAgent);
        if (chromeMatcher.find()) {
            return "Chrome " + chromeMatcher.group(1);
        }

        // 检查 Firefox
        Matcher firefoxMatcher = Pattern.compile(firefoxPattern).matcher(userAgent);
        if (firefoxMatcher.find()) {
            return "Firefox " + firefoxMatcher.group(1);
        }

        // 检查 Safari
        Matcher safariMatcher = Pattern.compile(safariPattern).matcher(userAgent);
        if (safariMatcher.find()) {
            return "Safari " + safariMatcher.group(1);
        }

        // 检查 Edge
        Matcher edgeMatcher = Pattern.compile(edgePattern).matcher(userAgent);
        if (edgeMatcher.find()) {
            return "Edge " + edgeMatcher.group(1);
        }

        // 检查 IE
        Matcher ieMatcher = Pattern.compile(iePattern).matcher(userAgent);
        if (ieMatcher.find()) {
            if (ieMatcher.group(1) != null) {
                return "Internet Explorer " + ieMatcher.group(1);
            } else {
                return "Internet Explorer " + ieMatcher.group(3);
            }
        }

        return browserInfo;
    }
    public static String getOSInfo(String userAgent) {
        // 定义一个正则表达式模式
        String windowsPattern = "Windows NT (\\d+\\.\\d+)";
        String macPattern = "Mac OS X (\\d+[._\\d]*)";
        String linuxPattern = "Linux";
        String androidPattern = "Android (\\d+\\.\\d+)";
        String iosPattern = "iPhone OS (\\d+_[\\d_]*)";

        // 使用正则表达式查找操作系统
        String osInfo = "Unknown OS";

        // 检查 Windows
        Matcher windowsMatcher = Pattern.compile(windowsPattern).matcher(userAgent);
        if (windowsMatcher.find()) {
            return "Windows " + windowsMatcher.group(1);
        }

        // 检查 macOS
        Matcher macMatcher = Pattern.compile(macPattern).matcher(userAgent);
        if (macMatcher.find()) {
            return "Macintosh " + macMatcher.group(1).replace('_', '.');
        }

        // 检查 Linux
        if (userAgent.contains("Linux")) {
            return "Linux";
        }

        // 检查 Android
        Matcher androidMatcher = Pattern.compile(androidPattern).matcher(userAgent);
        if (androidMatcher.find()) {
            return "Android " + androidMatcher.group(1);
        }

        // 检查 iOS
        Matcher iosMatcher = Pattern.compile(iosPattern).matcher(userAgent);
        if (iosMatcher.find()) {
            return "iOS " + iosMatcher.group(1).replace('_', '.');
        }

        return osInfo;
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
        log.error("Exception:{}\nMethod:{}\n", errorMessage, methodName);
        saveExceptionLog(exceptionLog);
    }

    @Override
    public Result saveExceptionLog(ExceptionLog exceptionLog) {
        boolean success = exceptionLogMapper.insert(exceptionLog) > 0;
        if (success)
            return Result.success();
        else
            return Result.error("保存日志失败");
    }

    @Override
    public Result getExceptionLogList(LocalDateTime startTime, LocalDateTime endTime) {
        startTime = startTime == null ? LocalDateTime.of(2005, 7, 24, 0, 0) : startTime;
        endTime = endTime == null ? LocalDateTime.now() : endTime;
        return Result.success(exceptionLogMapper.selectByTime(startTime, endTime));
    }

    @Override
    public Result deleteExceptionLog(Integer id) {
        boolean success = exceptionLogMapper.deleteById(id) > 0;
        if (success)
            return Result.success();
        else
            return Result.error("删除日志失败");
    }

    @Override
    public Result getOperationLog(LocalDateTime startTime, LocalDateTime endTime) {
        startTime = startTime == null ? LocalDateTime.of(2005, 7, 24, 0, 0) : startTime;
        endTime = endTime == null ? LocalDateTime.now() : endTime;
        return Result.success(operationLogMapper.selectByTime(startTime, endTime));
    }

    @Override
    public Result deleteOperationLog(Integer id) {
        boolean success = operationLogMapper.deleteById(id) > 0;
        if (success)
            return Result.success();
        else
            return Result.error("删除日志失败");
    }

    @Override
    public Result saveOperationLog(OperationLog operationLog) {
        boolean success = operationLogMapper.insert(operationLog) > 0;
        if (success)
            return Result.success();
        else
            return Result.error("保存日志失败");
    }

    public Result getLogFile() {
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
