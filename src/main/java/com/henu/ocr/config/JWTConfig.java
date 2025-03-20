package com.henu.ocr.config;

import com.henu.ocr.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JWTConfig {
    @Value("${security.jwt.expiration}")
    private long expiration;
    @Value("${security.jwt.secret}")
    private String secret;

    @PostConstruct // 容器初始化时触发
    public void init() {
        JWTUtil.EXPIRE_TIME = expiration;
        JWTUtil.SECRET = secret;
    }
}
