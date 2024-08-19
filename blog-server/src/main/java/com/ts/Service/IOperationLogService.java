package com.ts.Service;

import com.ts.Model.Entity.OperationLog;
import com.ts.Model.Entity.Result;

import java.time.LocalDateTime;

public interface IOperationLogService {
    Result getOperationLog(LocalDateTime startDate, LocalDateTime endDate);

    Result deleteOperationLog(Integer id);

    Result saveOperationLog(OperationLog operationLog);
}
