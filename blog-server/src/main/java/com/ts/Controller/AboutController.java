package com.ts.Controller;

import com.ts.Model.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ts.Constants.RedisConstants.ABOUT_TEXT_CACHE_KEY;


@RestController
public class AboutController{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/about")
    public Result getAbout() {
        String text=redisTemplate.opsForValue().get(ABOUT_TEXT_CACHE_KEY);
        return Result.success(text);
    }

    @PutMapping("/admin/about")
    public Result updateAbout(@RequestBody Map<String, String> requestBody) {
        String text = requestBody.get("text");
        redisTemplate.opsForValue().set(ABOUT_TEXT_CACHE_KEY, text);
        return Result.success();
    }



}
