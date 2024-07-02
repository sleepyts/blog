package com.ts.Controller;


import com.ts.Entity.Result;
import com.ts.VO.VerifyVO;
import com.ts.Utils.VerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static com.ts.Constants.RedisConstants.VERIFY_CACHE_EXPIRE_TIME;
import static com.ts.Constants.RedisConstants.VERIFY_CACHE_KEY;

@RestController
@RequestMapping("/verify")
@Slf4j
public class IdentifiyController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping()
    public Result getIdentify() {
        VerifyVO verifyVO= VerifyUtil.getVerifyVO();
        String str=verifyVO.getVar1()+"*"+verifyVO.getVar2()+"="+verifyVO.getVar1()*verifyVO.getVar2();
        redisTemplate.opsForValue().set(VERIFY_CACHE_KEY+verifyVO.getKey(),str ,VERIFY_CACHE_EXPIRE_TIME, TimeUnit.SECONDS);
        return Result.success(verifyVO);
    }
}
