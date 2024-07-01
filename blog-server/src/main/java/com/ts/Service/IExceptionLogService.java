package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.ExceptionLog;
import com.ts.Entity.Result;

import java.time.LocalDate;

public interface IExceptionLogService{
    Result saveExceptionLog(ExceptionLog exceptionLog);

    Result getExceptionLogList(LocalDate startDate, LocalDate endDate);

    Result deleteExceptionLog(Integer id);
}
