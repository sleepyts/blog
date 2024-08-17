package com.ts.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Entity.Category;
import com.ts.Entity.Result;
import com.ts.Mapper.CategoryMapper;
import com.ts.Service.ICategoryService;
import com.ts.Service.RedisService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ts.Constants.RedisConstants.*;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result getCategories() {
        List<Category> categories = this.list();
        return Result.success(categories);
    }

    @Override
    public Result updateCategory(Category category) {
        this.updateById(category);
        List<Integer> blogIds=categoryMapper.selectBlogIdByCategoryId(category.getId());
        List<String> blogKeys=new ArrayList<>();
        for (Integer blogId : blogIds){
            String key=BLOG_CONTENT_CACHE_KEY+":" + blogId;
            blogKeys.add(key);
        }
        redisService.delayDeleteTwice(blogKeys);
        return Result.success();
    }
}
