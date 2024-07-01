package com.ts.Service.Impl;


import com.ts.Annotation.RequestLog;
import com.ts.Entity.Result;
import com.ts.Entity.Settings;
import com.ts.Mapper.SettingsMapper;
import com.ts.Service.RedisService;
import com.ts.Service.SettingsService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ts.Constants.RedisConstants.*;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsMapper settingsMapper;
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedissonClient redissonClient;
    @Override
    @RequestLog
    public Result getSettings() {
        String key=SETTINGS_CACHE_KEY;
        String lockKey=SETTINGS_CACHE_LOCK;
        Settings setting=redisService.get(key);
        if(setting!=null) return Result.success(setting);

        RLock lock=redissonClient.getLock(lockKey);
        boolean lockable=lock.tryLock();
        try{
            if(!lockable) return Result.error("获取锁失败，请稍后再试");
            setting=settingsMapper.getSettings();
            redisService.set(key,setting,NORMAL_CACHE_EXPIRE_TIME);
            return Result.success(setting);
        }finally{
            lock.unlock();
        }
    }

    @Override
    @RequestLog
    public Result updateSettings(Settings settings) {
        String key=SETTINGS_CACHE_KEY;
        settingsMapper.updateSettings(settings);
        redisService.delayDeleteTwice(key);

        return Result.success();

    }
}
