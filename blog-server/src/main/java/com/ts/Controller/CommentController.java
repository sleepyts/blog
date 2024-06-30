package com.ts.Controller;


import com.ts.Entity.DTO.CommentDTO;
import com.ts.Entity.Result;
import com.ts.Service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class CommentController {

    @Autowired
    private ICommentService commentService;
    @PostMapping("/comment")
    public Result addComment(@RequestBody CommentDTO commentDTO) {

        return commentService.addComment(commentDTO);
    }

    @GetMapping("/comment/{id}")
    public Result getCommentById(@PathVariable("id") Long id) {

        return commentService.getCommentById(id);
    }

    @DeleteMapping("/admin/comment/{id}")
    public Result deleteCommentById(@PathVariable("id") Long id) {

        return commentService.deleteCommentById(id);
    }
    @GetMapping("/comment/page/{id}")
    public Result getCommentByPage(@PathVariable("id") Long id,
                                   @RequestParam(name="page", defaultValue="1") Integer page,
                                   @RequestParam(name="size", defaultValue="8") Integer size) {

        return commentService.getCommentByPage(id, page, size);
    }
}
