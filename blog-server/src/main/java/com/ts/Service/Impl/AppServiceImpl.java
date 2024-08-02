package com.ts.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Annotation.Cacheable;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.App;
import com.ts.Entity.Result;
import com.ts.Mapper.AppMapper;
import com.ts.Service.IAppService;
import com.ts.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ts.Constants.RedisConstants.APP_CACHE_KEY;

@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

    @Autowired
    private RedisService redisService;

    @Override
    @RequestLog
    @Cacheable(KEY = APP_CACHE_KEY)
    public Result getApps() {
        List<App> apps;
        apps = this.list();
        return Result.success(apps);
    }

    @Override
    @RequestLog
    public Result addApp(App app) {
        this.save(app);
        redisService.delayDeleteTwice(APP_CACHE_KEY);
        return Result.success();
    }

    @Override
    @RequestLog
    public Result deleteApp(Long id) {
        this.removeById(id);
        redisService.delayDeleteTwice(APP_CACHE_KEY);
        return Result.success();
    }

    @Override
    @RequestLog
    public Result updateApp(App app) {
        this.updateById(app);
        redisService.delayDeleteTwice(APP_CACHE_KEY);
        return Result.success();
    }

}
