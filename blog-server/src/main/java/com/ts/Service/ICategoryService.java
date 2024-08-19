package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Model.Entity.Category;
import com.ts.Model.Entity.Result;

public interface ICategoryService extends IService<Category> {
    Result getCategories();
    Result updateCategory(Category category);
}
