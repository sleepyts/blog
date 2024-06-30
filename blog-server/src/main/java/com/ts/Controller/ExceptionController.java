package com.ts.Controller;


import com.ts.Entity.Result;
import com.ts.Service.IExceptionLogService;
import com.ts.Service.Impl.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/exception")
@Slf4j
public class ExceptionController {

    @Autowired
    private LogService logService;
    @GetMapping("")
    public Result getException() {

        return Result.success(logService.getExceptionLogList());
    }

    @DeleteMapping("/{id}")
    public Result deleteException(@PathVariable("id") Integer id) {
        logService.deleteExceptionLog(id);
        return Result.success();
    }
}
