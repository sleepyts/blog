package com.ts.Service;

import com.ts.Model.Entity.ExceptionLog;
import com.ts.Model.Entity.Result;

import java.time.LocalDateTime;

public interface IExceptionLogService{
    Result saveExceptionLog(ExceptionLog exceptionLog);

    Result getExceptionLogList(LocalDateTime startDate, LocalDateTime endDate);

    Result deleteExceptionLog(Integer id);
}
