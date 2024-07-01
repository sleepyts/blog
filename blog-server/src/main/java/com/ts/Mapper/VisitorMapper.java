package com.ts.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    @Select("SELECT COUNT(*) FROM tb_visitor WHERE ip = #{ip}")
    Boolean isContains(String ip);

    @Select("SELECT * FROM tb_visitor WHERE ip = #{ip}")
    Visitor selectByIp(String ip);

    @Update("UPDATE tb_visitor SET last_visit_time = #{lastVisitTime} WHERE ip = #{ip}")
    Boolean updateLastVisit(Visitor visitor);
}