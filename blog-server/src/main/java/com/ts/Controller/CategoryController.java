package com.ts.Controller;


import com.ts.Entity.Category;
import com.ts.Entity.Result;
import com.ts.Service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/category")
    public Result getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping("/admin/category/{id}")
    public Result deleteCategory(@PathVariable("id") Long id) {
        return Result.success(categoryService.removeById(id));
    }

    @PostMapping("/admin/category")
    public Result addCategory(@RequestBody Category category) {
        return Result.success(categoryService.save(category));
    }

    @PutMapping("/admin/category")
    public Result updateCategory(@RequestBody Category category) {
        return Result.success(categoryService.updateCategory(category));
    }
}
