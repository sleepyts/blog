package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.Comment;
import com.ts.DTO.CommentDTO;
import com.ts.Entity.Result;

public interface ICommentService extends IService<Comment> {
    Result addComment(CommentDTO commentDTO);

    Result getCommentById(Long id);

    Result deleteCommentById(Long id);

    Result deleteCommentByBlogId(Long blogId);

    Result getCommentByPage(Long id, Integer page, Integer size);

}
