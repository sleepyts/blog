package com.ts.Controller;


import com.ts.Entity.Result;
import com.ts.Service.IExceptionLogService;
import com.ts.Service.Impl.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/exception")
@Slf4j
public class ExceptionController {

    @Autowired
    private LogService logService;
    @GetMapping("")
    public Result getException(@RequestParam(value="start", required=false)LocalDate start, @RequestParam(value="end", required=false) LocalDate end) {
        return Result.success(logService.getExceptionLogList(start, end));
    }

    @DeleteMapping("/{id}")
    public Result deleteException(@PathVariable("id") Integer id) {
        logService.deleteExceptionLog(id);
        return Result.success();
    }
}
