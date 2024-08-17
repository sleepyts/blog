package com.ts.Service.Impl;

import com.ts.Service.RedisService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Value("${config.redis.redisDeleteDelay}")
    private Long DELAY_TIME;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> void setList(String key, List<T> list) {
        redisTemplate.opsForValue().set(key, list);
    }

    @Override
    public <T> void setList(String key, List<T> list, Long expireTime) {
        redisTemplate.opsForValue().set(key, list, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public <T> List<T> getList(String key) {
        return (List<T>) redisTemplate.opsForValue().get(key);
    }

    @Override
    @Async
    public void delayDeleteTwice(String key) {
        redisTemplate.delete(key);
        try{
            Thread.sleep(DELAY_TIME);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        redisTemplate.delete(key);
    }

    @Override
    @Async
    public void delayDeleteTwice(String... keys) {
        for (String key : keys) {
            delayDeleteTwice(key);
        }
    }

    @Override
    @Async
    public void delayDeleteTwice(List<String> keys) {
        for (String key : keys) {
            delayDeleteTwice(key);
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, Long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }


}
