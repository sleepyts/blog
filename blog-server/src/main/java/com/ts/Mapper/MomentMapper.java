package com.ts.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ts.Entity.Moment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MomentMapper extends BaseMapper<Moment> {

    @Select("select * from tb_moment order by create_time desc")
    List<Moment> list();

    @Select("select count(id) from tb_moment")
    Integer count();


    @Select("select * from tb_moment order by create_time desc")
    IPage<Moment> selectPage(IPage<Moment> page);

    @Update("update tb_moment set visible =NOT visible where id = #{id}")
    boolean changeVisible(Integer id);
}
