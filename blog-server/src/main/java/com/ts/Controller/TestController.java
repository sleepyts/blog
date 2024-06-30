package com.ts.Controller;


import com.ts.Entity.Blog;
import com.ts.Entity.Result;
import com.ts.Entity.UVPV;
import com.ts.Mapper.UVPVMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;

import static com.ts.Utils.ipAddressUtils.getCityInfo;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {


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
        System.out.println(getCityInfo("218.199.207.23"));
        return Result.success("test");
    }
}
