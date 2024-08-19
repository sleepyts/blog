package com.ts.Mapper;

import com.ts.Model.Entity.UVPV;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UVPVMapper {

    @Insert("insert into tb_uvpv(uv,pv,date) values(#{uv},#{pv},#{date})")
    void insertUVPV(UVPV uvpv);


    @Select("select sum(uv) from tb_uvpv")
    Integer getTotalUV();

    @Select("select sum(pv) from tb_uvpv")
    Integer getTotalPV();
}
