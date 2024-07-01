package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    @Select("select * from tb_operation where operation_time >= #{startDate} and operation_time <= #{endDate}" +
            " order by operation_time desc")
    List<OperationLog> selectByTime(LocalDateTime startDate, LocalDateTime endDate);
}
