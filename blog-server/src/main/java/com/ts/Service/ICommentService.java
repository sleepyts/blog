package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Model.DTO.CommentDTO;
import com.ts.Model.Entity.Comment;
import com.ts.Model.Entity.Result;

public interface ICommentService extends IService<Comment> {
    Result addComment(CommentDTO commentDTO);

    Result getCommentById(Long id);

    Result deleteCommentById(Long id);

    Result deleteCommentByBlogId(Long blogId);

    Result getCommentByPage(Long id, Integer page, Integer size);

    Result adminAddComment(CommentDTO commentDTO);
}
