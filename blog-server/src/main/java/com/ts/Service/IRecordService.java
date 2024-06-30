package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.Blog;
import com.ts.Entity.Record;
import com.ts.Entity.Result;

public interface IRecordService extends IService<Record> {
    Result getRecord();
    Result addRecord(Blog blog);

    Result deleteRecordByBlogId(Long id);

    Result updateRecord(Blog blog);
}
