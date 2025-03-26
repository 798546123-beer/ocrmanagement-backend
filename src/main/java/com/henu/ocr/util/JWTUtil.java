package com.henu.ocr.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class JWTUtil {

    /**
     * Token有效期为8min
     */
    public static long EXPIRE_TIME;
    public static String SECRET;

    // 时间偏移量，默认设置为5分钟（单位：毫秒）
    public static long TIME_OFFSET = 5 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username, Integer userId) {
        try {
            // 根据密钥生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).withClaim("userId", String.valueOf(userId)).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            Date expiresAt = jwt.getExpiresAt();
            expiresAt.before(new Date(System.currentTimeMillis() + TIME_OFFSET));
            return true;
        } catch (Exception exception) {
            log.error("token验证失败", exception);
            return false;
        }
    }


    public static Map<String, String> getUserInfoByToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, String> map = new HashMap<>();
            map.put("username", jwt.getClaim("username").asString());
            map.put("userId", jwt.getClaim("userId").asString());
            return map;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,8min后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String sign(String username, Integer userId) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 附带user信息
        return JWT.create().withClaim("username", username).withClaim("userId", String.valueOf(userId)).withExpiresAt(date).sign(algorithm);
    }

    public static String refreshToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("username").asString();
            String userId = jwt.getClaim("userId").asString();
            return sign(username, Integer.parseInt(userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

