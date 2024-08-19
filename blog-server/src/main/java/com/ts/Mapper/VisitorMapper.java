package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Model.Entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    @Select("SELECT COUNT(*) FROM tb_visitor WHERE ip = #{ip}")
    Boolean isContains(String ip);

    @Select("SELECT * FROM tb_visitor WHERE ip = #{ip}")
    Visitor selectByIp(String ip);

    @Update("UPDATE tb_visitor SET last_visit_time = #{lastVisitTime} WHERE id = #{id}")
    Boolean updateLastVisit(Visitor visitor);

    @Select("SELECT ip FROM tb_visitor")
    List<String> selectAllIps();
}
