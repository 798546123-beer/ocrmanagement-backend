package com.henu.ocr.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henu.ocr.annotation.IgnoreToken;
import com.henu.ocr.util.JWTUtil;
import com.henu.ocr.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

import static com.henu.ocr.util.JWTUtil.getUserInfoByToken;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查到方法不是get put post delete就放行
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
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401,"token is blank")));
            return false;
        }
        if (!verifyToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401,"token is invalid")));
            return false;
        }
        return true;
    }

    private boolean verifyToken(String token) {
        Map<String, String> map = getUserInfoByToken(token);
        if (map == null || !map.containsKey("username") || !map.containsKey("userId")) {
            return false;
        }
        try {
            return JWTUtil.verify(token, map.get("username"), Integer.parseInt(map.get("userId")));
        } catch (NumberFormatException e) {
            log.error("token验证", e);
            return false;
        }
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
