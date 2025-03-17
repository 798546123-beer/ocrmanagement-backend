package com.henu.ocr.service.serviceImpl;

import com.henu.ocr.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl {
    @Resource
    private RedisUtil redisUtil;

    public boolean isLoginFailOvertimes(String username) {
        String key = com.henu.ocrbackend.constant.CommonConstant.LOGIN_FAIL + username;
        Integer failTime = redisUtil.get(key,Integer.class);
        if (failTime != null) {
            Integer val = Integer.parseInt(failTime.toString());
            return val > 5;
        }
        return false;
    }
    public boolean addLoginFailTimes(String username) throws Exception {
        try {
            String key = com.henu.ocrbackend.constant.CommonConstant.LOGIN_FAIL + username;
            Integer failTime = redisUtil.get(key, Integer.class);
            if (failTime != null) {
                Integer val = Integer.parseInt(failTime.toString());
                redisUtil.set(key, val + 1);
            } else {
                redisUtil.set(key, 1);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
