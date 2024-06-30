package com.ts.Mapper;

import com.ts.Entity.UVPV;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UVPVMapper {

    @Insert("insert into tb_uvpv(uv,pv,date) values(#{uv},#{pv},#{date})")
    void insertUVPV(UVPV uvpv);
}
