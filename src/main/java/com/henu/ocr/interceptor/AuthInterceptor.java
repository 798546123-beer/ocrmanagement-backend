package com.henu.ocr.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.henu.ocr.IgnoreToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

// 2. 实现认证拦截器
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否需要忽略认证
        if (isIgnoreAuth(handler)) {
            return true;
        }

        // 获取token（根据实际需求调整获取方式）
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing authentication token");
            return false;
        }

        // 这里可以添加token验证逻辑
        return verifyToken(token); // 需要实现token验证方法
    }

    private boolean verifyToken(String token) {
        return true;
    }

    private boolean isIgnoreAuth(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 检查方法上的注解
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(IgnoreToken.class)) {
                return true;
            }
            // 检查类上的注解
            Class<?> clazz = handlerMethod.getBeanType();
            return clazz.isAnnotationPresent(IgnoreToken.class);
        }
        return false;
    }
}
