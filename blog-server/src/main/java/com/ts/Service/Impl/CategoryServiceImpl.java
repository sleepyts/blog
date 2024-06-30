package com.ts.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Entity.Category;
import com.ts.Entity.Result;
import com.ts.Mapper.CategoryMapper;
import com.ts.Service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {


    @Override
    public Result getCategories() {
        List<Category> categories = this.list();
        return Result.success(categories);
    }
}
