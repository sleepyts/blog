package com.ts.Controller.Admin;


import com.ts.Entity.Result;
import com.ts.Service.IOperationLogService;
import com.ts.Service.Impl.LogService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/operation")
public class OperationController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    public Result getOperationLog() {
        return logService.getOperationLog();
    }

    @DeleteMapping("/{id}")

    public Result deleteOperationLogById(@PathVariable("id") int id) {
        return logService.deleteOperationLog(id);
    }
}
