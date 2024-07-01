package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.OperationLog;
import com.ts.Entity.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOperationLogService {
    Result getOperationLog(LocalDateTime startDate, LocalDateTime endDate);

    Result deleteOperationLog(Integer id);

    Result saveOperationLog(OperationLog operationLog);
}
