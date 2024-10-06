package com.ts.Controller.Admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.Mapper.TaskLogMapper;
import com.ts.Model.Entity.Result;
import com.ts.Model.Entity.TaskLog;
import com.ts.Model.VO.PageVO;
import com.ts.Service.Impl.LogService;
import com.ts.Service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/operation")
public class OperationController {

    @Autowired
    private LogService logService;
    @Autowired
    private TaskLogService taskLogService;

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

    @GetMapping("/tasklog")
    public Result getVisitorLog(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page
            ,@RequestParam(value= "pageSize",defaultValue = "10" ) Integer pageSize){
        IPage<TaskLog> pageSel = new Page<>(page, pageSize);
        LambdaQueryWrapper<TaskLog> wrapper = Wrappers.lambdaQuery(TaskLog.class)
                .orderByDesc(TaskLog::getCreateTime);
        IPage<TaskLog> taskLogPage = taskLogService.page(pageSel,wrapper);

        PageVO pageVO=new PageVO(
                (int) taskLogPage.getTotal(),
                taskLogPage.getRecords()
        );
        return Result.success(pageVO);


    }
}
