package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.Blog;
import com.ts.Entity.Result;

public interface IBlogService extends IService<Blog> {

    Result queryBlog(int page);
    Result addBlog(Blog blog);
    Result updateBlog(Blog blog);
    Result deleteBlog(Long id);
    Result getCommentByPage(Integer current, Integer size);
    Result getBlogThumbnail();
    Result getBlogDetail(Integer id);
    Result getBlogList();
    Result getBlogContent(int id);
}
