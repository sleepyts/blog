package com.ts.Controller;


import com.ts.Entity.Result;
import com.ts.Service.IRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
@Slf4j
public class RecordController {
    @Autowired
    private IRecordService recordService;

    @GetMapping()
    public Result getRecord() {

        return recordService.getRecord();
    }
}
