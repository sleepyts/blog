package com.ts.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.App;
import com.ts.Entity.Result;
import com.ts.Mapper.AppMapper;
import com.ts.Service.IAppService;
import com.ts.Service.RedisService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ts.Constants.RedisConstants.*;


@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissonClient redissonClient;
    
    @Override
    @RequestLog
    public Result getApps() {
        List<App> apps = new ArrayList<>();
        String key=APP_CACHE_KEY;
        apps=redisService.get(key);

        if(apps!=null&& !apps.isEmpty()){
            return Result.success(apps);
        }

        String lockKey=APP_CACHE_LOCK;
        RLock lock=redissonClient.getLock(lockKey);
        boolean isLockable=lock.tryLock();

        try{
            if(!isLockable) return Result.error("当前访问人数过多，请稍后再试！");
            apps=this.list();
            if(!apps.isEmpty()){
                redisService.setList(key,apps,APP_CACHE_EXPIRE_TIME);
            }
        }finally{
            lock.unlock();
        }
        return Result.success(apps);
    }

    @Override
    public Result addApp(App app) {
        this.save(app);
        redisService.delayDeleteTwice(APP_CACHE_KEY);
        return Result.success();
    }

    @Override
    public Result deleteApp(Long id) {
        this.removeById(id);
        redisService.delayDeleteTwice(APP_CACHE_KEY);
        return Result.success();
    }

    @Override
    public Result updateApp(App app) {
        this.updateById(app);
        redisService.delayDeleteTwice(APP_CACHE_KEY);
        return Result.success();
    }


}
