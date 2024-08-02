package com.ts.Controller.Admin;


import com.ts.Entity.Result;
import com.ts.Service.Impl.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/operation")
public class OperationController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    public Result getOperationLog(@RequestParam(value = "start", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                  LocalDateTime start,
                                  @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                  LocalDateTime end) {
        return logService.getOperationLog(start, end);
    }

    @DeleteMapping("/{id}")
    public Result deleteOperationLogById(@PathVariable("id") int id) {
        return logService.deleteOperationLog(id);
    }

    @GetMapping("/log")
    public Result getLogFile(){
        return logService.getLogFile();
    }
}
