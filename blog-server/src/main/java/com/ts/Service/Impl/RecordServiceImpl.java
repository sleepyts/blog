package com.ts.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Annotation.Cacheable;
import com.ts.Annotation.RequestLog;
import com.ts.Mapper.RecordMapper;
import com.ts.Model.Entity.Blog;
import com.ts.Model.Entity.Record;
import com.ts.Model.Entity.Result;
import com.ts.Model.VO.RecordVO;
import com.ts.Service.IRecordService;
import com.ts.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ts.Constants.RedisConstants.RECORD_CACHE_KEY;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Autowired
    private RedisService redisService;

    /*
     * 获取记录列表
     * @param null
     * @return Result<List<RecordVO>>
     */
    @Override
    @RequestLog
    @Cacheable(KEY = RECORD_CACHE_KEY)
    public Result getRecord() {
        List<RecordVO> recordVOList;
        Map<Integer, List<Record>> mp = new HashMap<>();
        recordVOList = new ArrayList<>();
        List<Record> recordList = this.list();
        for (Record record : recordList) {
            int year = record.getCreateTime().getYear();
            mp.putIfAbsent(year, new ArrayList<>());
            mp.get(year).add(record);
        }
        for (Map.Entry<Integer, List<Record>> entry : mp.entrySet()) {
            RecordVO recordVO = new RecordVO();
            recordVO.setYear(String.valueOf(entry.getKey()));
            recordVO.setRecordList(entry.getValue());
            recordVOList.add(recordVO);
        }
        recordVOList.sort((o1, o2) -> o2.getYear().compareTo(o1.getYear()));
        for (RecordVO recordVO : recordVOList)
            recordVO.getRecordList().sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        return Result.success(recordVOList);
    }

    /*
     * 添加记录
     * @param blog 博客实体类
     * @return Result
     */
    @Override
    @RequestLog
    public Result addRecord(Blog blog) {
        Record record = new Record();
        record.setTitle(blog.getTitle());
        record.setDate(LocalDate.now());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        record.setBlogId(blog.getId());
        save(record);
        redisService.delayDeleteTwice(RECORD_CACHE_KEY);
        return Result.success();
    }

    /*
     * 删除记录
     * @param blogId 博客id
     * @return Result
     */
    @Override
    @RequestLog
    public Result deleteRecordByBlogId(Long blogId) {
        this.remove(new QueryWrapper<Record>().eq("blog_id", blogId));
        redisService.delayDeleteTwice(RECORD_CACHE_KEY);
        return Result.success();
    }

    /*
     * 更新记录
     * @param blog 博客实体类
     * @return Result
     */
    @Override
    @RequestLog
    public Result updateRecord(Blog blog) {
        Record record = new Record();
        record.setBlogId(blog.getId());
        record.setTitle(blog.getTitle());
        record.setUpdateTime(LocalDateTime.now());
        this.update(record, new QueryWrapper<Record>().eq("blog_id", blog.getId()));

        redisService.delayDeleteTwice(RECORD_CACHE_KEY);
        return Result.success();
    }


}
