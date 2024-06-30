package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.ExceptionLog;
import com.ts.Entity.Result;

public interface IExceptionLogService{
    Result saveExceptionLog(ExceptionLog exceptionLog);

    Result getExceptionLogList();

    Result deleteExceptionLog(Integer id);
}
