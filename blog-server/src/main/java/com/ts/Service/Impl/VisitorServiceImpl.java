package com.ts.Service.Impl;

import com.ts.Annotation.RequestLog;
import com.ts.Mapper.VisitorMapper;
import com.ts.Model.Entity.Result;
import com.ts.Service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;


    @Override
    public Result getVisitor() {
        return Result.success(visitorMapper.selectList(null));
    }

    @Override
    @RequestLog
    public Result deleteVisitorById(Integer id) {
        return Result.success(visitorMapper.deleteById(id));
    }

    @Override
    public Result getVisitorCount() {
        return Result.success(visitorMapper.selectCount(null));
    }
}
