package com.ts.Controller.Admin;


import com.ts.Entity.Result;
import com.ts.Service.IBlogService;
import com.ts.Service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ts.Constants.RedisConstants.PV_CACHE_KEY;
import static com.ts.Constants.RedisConstants.UV_CACHE_KEY;

@RestController
@RequestMapping("/admin/dashboard")
public class DashBordController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICommentService commentService;
    @GetMapping("/pv")
    public Result getPV() {
        return Result.success(redisTemplate.opsForValue().get(PV_CACHE_KEY));
    }

    @GetMapping("/uv")
    public Result getUV() {
        return Result.success(redisTemplate.opsForSet().size(UV_CACHE_KEY));
    }

    @GetMapping("/totalArticle")
    public Result getTotalArticle() {
        Integer totalArticle = Math.toIntExact(blogService.count());
        return Result.success(totalArticle);
    }

    @GetMapping("/totalComment")
    public Result getTotalComment() {
        Integer totalComment = Math.toIntExact(commentService.count());
        return Result.success(totalComment);
    }
}
