
package com.henu.ocr.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OCR API 文档")
                        .version("1.0")
                        .description("图像识别服务接口文档"))
                .externalDocs(new ExternalDocumentation()
                        .description("完整文档说明")
                        .url("/doc.html"));
    }
}
