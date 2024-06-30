package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.OperationLog;
import com.ts.Entity.Result;

public interface IOperationLogService {
    Result getOperationLog();

    Result deleteOperationLog(Integer id);

    Result saveOperationLog(OperationLog operationLog);
}
