package com.ts.Service.Impl;


import com.ts.Annotation.Cacheable;
import com.ts.Annotation.RequestLog;
import com.ts.Mapper.SettingsMapper;
import com.ts.Model.Entity.Result;
import com.ts.Model.Entity.Settings;
import com.ts.Model.VO.SettingsVO;
import com.ts.Service.RedisService;
import com.ts.Service.SettingsService;
import org.springframework.beans.BeanUtils;
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
        SettingsVO setting=new SettingsVO();
        BeanUtils.copyProperties(settingsMapper.getSettings(),setting);
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
