package com.ts.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.Annotation.RequestLog;
import com.ts.Mapper.VisitorMapper;
import com.ts.Model.Entity.Result;
import com.ts.Model.Entity.Visitor;
import com.ts.Service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;


    @Override
    public Result getVisitor() {
        LambdaQueryWrapper<Visitor> visitorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        visitorLambdaQueryWrapper.orderBy(true, false, Visitor::getLastVisitTime);
        return Result.success(visitorMapper.selectList(visitorLambdaQueryWrapper));
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
