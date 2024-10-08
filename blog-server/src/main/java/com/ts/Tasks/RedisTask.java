package com.ts.Tasks;

import com.ts.Model.Entity.UVPV;
import com.ts.Service.IAppService;
import com.ts.Service.IRecordService;
import com.ts.Service.IUVPVService;
import com.ts.Service.SettingsService;
import com.ts.Utils.BloomFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.ts.Constants.RedisConstants.PV_CACHE_KEY;
import static com.ts.Constants.RedisConstants.UV_CACHE_KEY;

@Component
public class RedisTask {

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private IUVPVService uvpvService;
    @Autowired
    private IAppService appService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BloomFilters bloomFilters;

    /**
     * 预热缓存
     * time : 01:00
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void readRedis() {
        recordService.getRecord();
        appService.getApps();
        settingsService.getSettings();
    }

    /**
     * 清除每日访问量,每日访客量缓存,当日IP布隆过滤器
     * time : 01:30 防止与预热缓存并发问题
     */
    @Scheduled(cron = "0 30 1 * * ? ")
    public void clearPVAndUV() {

        // 保存当日UVPV
        UVPV uvpv = new UVPV();
        Long uv = redisTemplate.opsForHyperLogLog().size(UV_CACHE_KEY);
        String pv = redisTemplate.opsForValue().get(PV_CACHE_KEY);
        if (uv != null) {
            uvpv.setUv(uv.intValue());
        }
        if (pv != null) {
            uvpv.setPv(Integer.valueOf(pv));
        }
        uvpv.setDate(LocalDate.now().minusDays(1));
        uvpvService.addUVPV(uvpv);

        // 进行其他操作
        bloomFilters.clearDaily();
        redisTemplate.delete(PV_CACHE_KEY);
        redisTemplate.delete(UV_CACHE_KEY);
    }

}
