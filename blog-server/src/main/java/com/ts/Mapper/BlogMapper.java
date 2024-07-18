package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Entity.Blog;
import com.ts.VO.BlogRandomVO;
import com.ts.VO.BlogVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {

    @Select("select name from tb_category where id = #{id}")
    String getCategoryName(int id);

    //TODO 写SQL 查询返回BLOG VO
    @Select("")
    List<BlogVO> selectVO();

    @Select("select id,title,create_time from tb_blog order by rand() limit 3")
    List<BlogRandomVO> selectRandomVO();
}
