package com.henu.ocr;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.UnknownHostException;
/**
 * @author 25612
 */
//@EnableTransactionManagement(proxyTargetClass = true)
//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        JpaRepositoriesAutoConfiguration.class})
//@MapperScan("com.henu.ocrbackend.mapper")
//@PropertySource("classpath:application.yml")
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(StartApplication.class, args);
    }
}
