package com.henu.ocr.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtil(
            RedisTemplate<String, Object> redisTemplate,
            StringRedisTemplate stringRedisTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setWithExpire(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    // ------------------------ 原生方法快捷访问 ------------------------
    public ValueOperations<String, Object> opsForValue() {
        return redisTemplate.opsForValue();
    }
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }
}