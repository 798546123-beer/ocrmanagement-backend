package com.henu.ocr.util;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoderUtil {
    @Bean
    public BCryptPasswordEncoder bCryptEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    public static String encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.encode(password);
    }
    public static boolean matches(String password, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.matches(password, encodedPassword);
    }
}
