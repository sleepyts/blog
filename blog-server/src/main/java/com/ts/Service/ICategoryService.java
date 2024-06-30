package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.Category;
import com.ts.Entity.Result;

public interface ICategoryService extends IService<Category> {
    Result getCategories();
}
