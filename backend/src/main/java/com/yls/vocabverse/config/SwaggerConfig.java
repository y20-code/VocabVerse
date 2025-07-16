// 升级后的 SwaggerConfig.java
package com.yls.vocabverse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 1. 定义一个安全方案 (Security Scheme)，告诉 Swagger 我们用的是什么认证方式
        final String securitySchemeName = "bearerAuth"; // 给这个方案起个名字

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization") // 方案名称
                .type(SecurityScheme.Type.HTTP) // 类型是 HTTP
                .scheme("bearer") //具体的 scheme 是 bearer
                .bearerFormat("JWT") // Bearer 的格式是 JWT
                .in(SecurityScheme.In.HEADER) // Token 存在于请求头 (Header) 中
                .description("在此处输入 JWT Token (无需添加 'Bearer ' 前缀)");

        // 2. 定义一个全局的安全要求 (Security Requirement)
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName); // 应用我们刚刚定义的 'bearerAuth' 方案

        // 3. 构建最终的 OpenAPI 对象
        return new OpenAPI()
                // 3.1 添加组件，把我们定义的安全方案注册进去
                .components(new Components().addSecuritySchemes(securitySchemeName, securityScheme))
                // 3.2 添加全局安全要求，让所有接口右上角都出现小锁图标
                .addSecurityItem(securityRequirement)
                // 3.3 设置 API 的基本信息 (你原来就有的部分)
                .info(new Info()
                        .title("VocabVerse API 文档")
                        .version("1.0.0")
                        .description("这是 VocabVerse 项目的后端 API 接口文档。"));
    }
}