package com.ts.Service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.Annotation.Cacheable;
import com.ts.Annotation.RequestLog;
import com.ts.Entity.Moment;
import com.ts.Entity.Result;
import com.ts.Mapper.MomentMapper;
import com.ts.Service.MomentService;
import com.ts.Service.RedisService;
import com.ts.VO.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ts.Constants.RedisConstants.COMMENT_CACHE_KEY;
import static com.ts.Constants.RedisConstants.MOMENT_CACHE_KEY;


@Service
public class MomentServiceImpl implements MomentService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MomentMapper momentMapper;

    @Override
    @RequestLog
    public Result addMoment(Moment moment) {
        momentMapper.insert(moment);
        deleteCache();
        return Result.success();
    }

    @Override
    @RequestLog
    @Cacheable(KEY =MOMENT_CACHE_KEY)
    public Result getMomentByPage(int currentPage, int pageSize) {
        pageSize=7;
        PageVO pageVO = new PageVO();
        Page<Moment> page = new Page<>(currentPage, pageSize);
        momentMapper.selectPage(page);

        pageVO.setRows(page.getRecords());
        int total=momentMapper.count();

        pageVO.setTotal(total);
        return Result.success(pageVO);
    }

    @Override
    public Result getMoments() {
        return Result.success(momentMapper.list());
    }

    @Override
    @RequestLog
    public Result deleteMoment(int id) {
        momentMapper.deleteById(id);
        deleteCache();
        return Result.success();
    }

    @Override
    @RequestLog
    public Result updateMoment(Moment moment) {
        momentMapper.updateById(moment);
        deleteCache();
        return Result.success();
    }

    @Override
    @RequestLog
    public Result changeVisibility(int id) {
        if(momentMapper.changeVisible(id)) {
            deleteCache();
            return Result.success();
        }
        else return Result.error("未知错误");
    }

    @Override
    public Result getRecentMoments() {
        PageVO firstPage =(PageVO)(getMomentByPage(1, 7).getData());
        List<Moment> moments = (List<Moment>) firstPage.getRows();
        List<Moment> recentMoments = moments.subList(0, Math.min(moments.size(), 5));
        return Result.success(recentMoments);
    }

    private void deleteCache() {
        String pattern = MOMENT_CACHE_KEY +":" +"*";
        Set<String> keys = new HashSet<>();
        try (Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(redisConnection -> redisConnection.scan(ScanOptions.scanOptions().match(pattern).count(1000).build()))) {
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during scanning keys", e);
        }
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
