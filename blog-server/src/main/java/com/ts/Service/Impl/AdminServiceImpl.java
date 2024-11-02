package com.ts.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Annotation.RequestLog;
import com.ts.Mapper.AdminMapper;
import com.ts.Model.Entity.Admin;
import com.ts.Model.Entity.Result;
import com.ts.Service.IAdminService;
import com.ts.Service.RedisService;
import com.ts.Utils.JwtUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ts.Constants.RedisConstants.ADMIN_LOGIN_EXPIRE_TIME;
import static com.ts.Constants.RedisConstants.ADMIN_LOGIN_KEY;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    private final RedisService redisService;
    private final JwtUtils jwtUtils;

    @Override
    @RequestLog
    public Result login(Admin admin, HttpSession session) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        LambdaQueryWrapper<Admin> adminQueryWrapper = new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username)
                .eq(Admin::getPassword, password);
        Admin one = this.getOne(adminQueryWrapper);
        if (one == null)
            return Result.error("用户名或密码错误");

        String token = jwtUtils.createToken(admin.getUsername());
        redisService.set(ADMIN_LOGIN_KEY, token, ADMIN_LOGIN_EXPIRE_TIME);

        return Result.success(token);
    }
}
