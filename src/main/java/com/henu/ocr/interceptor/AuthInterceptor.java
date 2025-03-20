package com.henu.ocr.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.henu.ocr.IgnoreToken;
import com.henu.ocr.util.JWTUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

import static com.henu.ocr.util.JWTUtil.getUserInfoByToken;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //查到方法不是get put post delete就放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
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
        return true;
//        return verifyToken(token);
    }

    private boolean verifyToken(String token) {
        Map<String,String> map=getUserInfoByToken(token);
        System.out.println("verify token"+map);
        return JWTUtil.verify(token, map.get("username"), Integer.valueOf(map.get("userId")));
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
