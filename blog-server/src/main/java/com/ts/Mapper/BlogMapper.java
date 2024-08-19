package com.ts.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.Model.Entity.Blog;
import com.ts.Model.VO.BlogRandomVO;
import com.ts.Model.VO.BlogVO;
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

    @Select("select id from tb_blog")
    List<Integer> selectAllBlogIds();

    @Select("select tb_blog.id, title, img, description, c.name as category_name, create_time from tb_blog " +
            "left join tb_category c on category_id = c.id " +
            "where category_id = #{categoryId} " +
            "order by create_time desc " +
            "limit #{page},5")
    List<BlogVO> getBlogByCategoryId(int page,int categoryId);

    @Select("select count(*) from tb_blog where category_id = #{categoryId}")
    int getBlogCountByCategoryId (int categoryId);
}
