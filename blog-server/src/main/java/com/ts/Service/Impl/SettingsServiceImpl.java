package com.ts.Service.Impl;


import com.ts.Annotation.Cacheable;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.Result;
import com.ts.Entity.Settings;
import com.ts.Mapper.SettingsMapper;
import com.ts.Service.RedisService;
import com.ts.Service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ts.Constants.RedisConstants.SETTINGS_CACHE_KEY;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsMapper settingsMapper;
    @Autowired
    private RedisService redisService;
    @Override
    @RequestLog
    @Cacheable(KEY=SETTINGS_CACHE_KEY)
    public Result getSettings() {
        Settings setting;
        setting = settingsMapper.getSettings();
        return Result.success(setting);
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
