package com.ts.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Mapper.AdminMapper;
import com.ts.Model.Entity.Admin;
import com.ts.Model.Entity.Result;
import com.ts.Service.IAdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ts.Constants.RedisConstants.ADMIN_LOGIN_EXPIRE_TIME;
import static com.ts.Constants.RedisConstants.ADMIN_LOGIN_KEY;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result login(Admin admin, HttpSession session) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        Admin user=query().eq("username",username).eq("password",password).one();
        if(user==null){
            return Result.error("用户名或密码错误");
        }

        String token= UUID.randomUUID().toString();
        Map<String,Object> map= BeanUtil.beanToMap(user,new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true)
                .setFieldValueEditor((field,value)-> value.toString()));
        redisTemplate.opsForHash().putAll(ADMIN_LOGIN_KEY+token,map);
        redisTemplate.expire(ADMIN_LOGIN_KEY+token,ADMIN_LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);

        return Result.success(token);
    }
}
