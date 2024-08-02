package com.ts.Service;

import com.ts.Entity.ExceptionLog;
import com.ts.Entity.Result;

import java.time.LocalDateTime;

public interface IExceptionLogService{
    Result saveExceptionLog(ExceptionLog exceptionLog);

    Result getExceptionLogList(LocalDateTime startDate, LocalDateTime endDate);

    Result deleteExceptionLog(Integer id);
}
