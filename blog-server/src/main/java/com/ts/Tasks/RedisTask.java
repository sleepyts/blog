package com.ts.Tasks;

import com.ts.Entity.UVPV;
import com.ts.Service.IBlogService;
import com.ts.Service.IRecordService;
import com.ts.Service.IUVPVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

import static com.ts.Constants.RedisConstants.PV_CACHE_KEY;
import static com.ts.Constants.RedisConstants.UV_CACHE_KEY;

@Component
public class RedisTask {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private IRecordService recordService;

    @Autowired
    private IUVPVService uvpvService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 预热缓存
     * time : 01:00
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void readRedis(){
        blogService.queryBlog();
        recordService.getRecord();
    }

    /**
     * 清除每日访问量和每日访客量缓存
     * time : 01:30 防止与预热缓存并发问题
     */
    @Scheduled(cron = "0 30 1 * * ? ")
    public void clearPVAndUV(){
        UVPV uvpv=new UVPV();
        Long uv= redisTemplate.opsForSet().size(UV_CACHE_KEY);
        String pv= redisTemplate.opsForValue().get(PV_CACHE_KEY);
        if(uv!=null){
            uvpv.setUv(uv.intValue());
        }
        if(pv!=null){
            uvpv.setPv(Integer.valueOf(pv));
        }
        uvpv.setDate(LocalDate.now().minusDays(1));
        uvpvService.addUVPV(uvpv);
        redisTemplate.delete(PV_CACHE_KEY);
        redisTemplate.delete(UV_CACHE_KEY);
    }

}