package com.henu.ocr.util;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Slf4j
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
        setWithExpire(key, value, timeout, TimeUnit.SECONDS);
    }

    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        return type.cast(value);
    }

    public Boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (RedisException e) {
            log.error("Redis delete failed: {}", e.getMessage());
            return false;
        }
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