package com.henu.ocr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableCaching
@Slf4j
public class StartApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(StartApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ConfigurableApplicationContext context) {
        return args -> {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            int port = context.getEnvironment().getProperty("server.port", Integer.class, 8080);
            log.info("启动成功，访问地址: http://{}:{}", ip, port);
            log.info("接口文档地址:http://{}:{}/swagger-ui/index.html", ip, port);
        };
    }
}