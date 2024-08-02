package com.ts.Controller;


import com.ts.Entity.Result;
import com.ts.Service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private MomentService momentService;

    @GetMapping("/hello")
    public Result hello() {
        return Result.success("hello");
    }

    @GetMapping("/world")
    public Result world() {
        return Result.success("world");
    }

    @GetMapping("")
    public Result test() {
        momentService.getMomentByPage(1,10);
        return Result.success("test");
    }
}
