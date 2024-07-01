package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Entity.ExceptionLog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

    @Select("select * from tb_exception where create_time >= #{startDate} and create_time <= #{endDate}")
    List<ExceptionLog> selectByTime(LocalDateTime startDate, LocalDateTime endDate);
}
