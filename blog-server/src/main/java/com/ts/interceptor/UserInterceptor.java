
package com.ts.interceptor;

import com.ts.Model.Entity.Visitor;
import com.ts.Utils.Holder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.ts.Utils.ipAddressUtils.getClientIp;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        // 获取客户端IP
        String ip = getClientIp(request.getHeader("X-Forwarded-For"),
                request.getHeader("X-Real-IP"),
                request.getRemoteAddr());
        Visitor visitor = new Visitor();
        visitor.setIp(ip);
        visitor.setUserAgent(request.getHeader("User-Agent"));
        Holder.setCurrentVisitor(visitor);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Holder.removeCurrentVisitor();
    }
}