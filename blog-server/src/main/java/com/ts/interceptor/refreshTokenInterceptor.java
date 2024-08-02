package com.ts.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.ts.Entity.Admin;
import com.ts.Utils.Holder;
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

        Map<Object, Object> map = redisTemplate.opsForHash().entries(ADMIN_LOGIN_KEY + token);
        if (map.isEmpty()) {
            Holder.removeAdmin();
            return true;
        }

        Admin admin = new Admin();
        BeanUtil.fillBeanWithMap(map, admin, false);
        Holder.setAdmin(admin);
        return true;
    }
}
