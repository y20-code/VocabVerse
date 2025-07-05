package com.yls.vocabverse.config;

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
                        .title("Vocabverse API 文档")
                        .version("1.0.0")
                        .description("这是 Vocabverse 项目的后端 API 接口文档，基于 OpenAPI 3.0 规范。"));
    }
}
