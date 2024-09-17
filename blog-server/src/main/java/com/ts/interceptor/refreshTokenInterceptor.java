package com.ts.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ts.Model.Entity.Admin;
import com.ts.Utils.Holder;
import com.ts.Utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static com.ts.Constants.RedisConstants.ADMIN_LOGIN_KEY;

public class refreshTokenInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate redisTemplate;
    public refreshTokenInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null)
            return true;

        String username= SpringUtil.getBean(JwtUtils.class).getUserName(token);
        if(username==null){
            Holder.removeAdmin();
            return true;
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        Holder.setAdmin(admin);
        return true;
    }
}
