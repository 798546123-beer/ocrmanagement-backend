package com.henu.ocr.test;

import com.henu.ocr.util.RedisUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.embedded.RedisServer;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTest {
    private static RedisServer redisServer;
    private static int redisPort;
    @Resource
    private RedisUtil redisService;

    private static int findAvailablePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    @BeforeAll
    static void startRedis() throws IOException {
        redisPort = findAvailablePort(); // 获取可用端口
        redisServer = RedisServer.newRedisServer()
                .port(redisPort)
                .setting("bind 127.0.0.1")
                .build();
        redisServer.start();
    }


    @AfterAll
    static void stopRedis() throws IOException {
        redisServer.stop();
    }

    @Test
    void testConnection() {
        String pong = redisService.getRedisTemplate()
                .getConnectionFactory()
                .getConnection().ping();
        assertEquals("PONG", pong);
    }

    @Test
    public void testRedis() {
        String key = "testKey";
        String value = "testValue";
        redisService.set(key, value);
        Object retrievedValue = redisService.get(key);

        assertEquals(value, retrievedValue);
        // 验证 Redis 存储的值是否一致

    }
}
