package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
