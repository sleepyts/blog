package com.ts.Config;

import com.ts.interceptor.UserInterceptor;
import com.ts.interceptor.adminLoginInterceptor;
import com.ts.interceptor.refreshTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

        @Autowired
        private StringRedisTemplate stringRedisTemplate;

        private final Integer FIRST_INTERCEPTOR = 0;
        private final Integer SECOND_INTERCEPTOR = 1;


        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new adminLoginInterceptor())
                                .addPathPatterns("/admin/**")
                                .excludePathPatterns("/admin/login")
                                .order(SECOND_INTERCEPTOR);
                registry.addInterceptor(new refreshTokenInterceptor(stringRedisTemplate))
                                .addPathPatterns("/**")
                                .order(FIRST_INTERCEPTOR);
                registry.addInterceptor(new UserInterceptor())
                                .addPathPatterns("/**")
                                .excludePathPatterns("/admin/**")
                                .order(FIRST_INTERCEPTOR);
        }

}
