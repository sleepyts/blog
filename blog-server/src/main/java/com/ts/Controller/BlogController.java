package com.ts.Controller;


import com.ts.Entity.Blog;
import com.ts.Entity.Result;
import com.ts.Service.IBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BlogController {

    @Autowired
    private IBlogService blogService;
    @GetMapping("/Blog")
    public Result getBlog(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        return blogService.queryBlog(page);
    }

    @GetMapping("/Blog/{id}")
    public Result getBlogDetail(@PathVariable("id") Integer id) {
        return blogService.getBlogDetail(id);
    }

    @GetMapping("/admin/Blog/content/{id}")
    public Result getBlogContent(@PathVariable("id") Integer id) {
        return blogService.getBlogContent(id);
    }

    @GetMapping("/admin/Blog")
    public Result getBlogList() {
        return blogService.getBlogList();
    }
    @PostMapping("/admin/Blog")
    public Result addBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

    @PutMapping("/admin/Blog")
    public Result updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }

    @DeleteMapping("/admin/Blog/{id}")
    public Result deleteBlog(@PathVariable("id") Long id) {
        return blogService.deleteBlog(id);
    }

    @GetMapping("/admin/Blog/thumbnail")
    public Result getBlogThumb() {
        return blogService.getBlogThumbnail();
    }

    @GetMapping("/Blog/random")
    public Result getRandomBlog() {
        return blogService.getRandomBlog();
    }

}
