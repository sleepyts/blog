
package com.ts.interceptor;

import com.ts.Entity.Visitor;
import com.ts.Utils.Holder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.ts.Utils.ipAddressUtils.getClientIp;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        String ip = getClientIp(request.getHeader("X-Forwarded-For"),
                request.getHeader("X-Real-IP"),
                request.getRemoteAddr());
        Visitor visitor = new Visitor();
        visitor.setIp(ip);
        Holder.setCurrentVisitor(visitor);
        return true;
    }
}