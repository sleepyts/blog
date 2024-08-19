package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Model.Entity.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {


    @Select("select id from tb_blog where category_id = #{categoryId}")
    List<Integer> selectBlogIdByCategoryId(Integer categoryId);
}
