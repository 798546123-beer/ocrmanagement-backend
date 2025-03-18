package com.henu.ocr.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
    /**
     * @param response
     * @param code
     * @param errorMsg
     */
    public static void responseError(ServletResponse response, Integer code, String errorMsg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // issues/I4YH95浏览器显示乱码问题
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        Result jsonResult = new Result(code, errorMsg);
        jsonResult.setSuccess(false);
        OutputStream os = null;
        try {
            os = httpServletResponse.getOutputStream();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(code);
            os.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username, Integer userId) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).withClaim("userId", userId).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    public static Map<String,String> getUserInfoByToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String,String> map = new HashMap<>();
            map.put("username",jwt.getClaim("username").asString());
            map.put("userId",jwt.getClaim("userId").asString());
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
    public static String sign(String username,Integer userId) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 附带user信息
        return JWT.create().withClaim("username", username).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }
}

