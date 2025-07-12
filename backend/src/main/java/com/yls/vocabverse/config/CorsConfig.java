package com.yls.vocabverse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置 (CORS Configuration)
 *
 * @author 杨林森
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建 CorsConfiguration 对象，这是CORS配置的核心
        CorsConfiguration config = new CorsConfiguration();

        // 2. 设置允许的来源 (origin)
        // 在开发环境下，我们通常允许来自前端开发服务器的地址
        config.addAllowedOrigin("http://localhost:5173"); 
        // 如果你有其他前端地址，也可以一并添加
        // 注意：在生产环境中，这里应该配置你的前端线上域名
        // config.addAllowedOrigin("https://www.your-production-site.com");

        // 3. 设置是否发送 Cookie 等凭证信息
        config.setAllowCredentials(true);

        // 4. 设置允许的请求头
        // 允许所有的请求头，包括自定义的请求头
        config.addAllowedHeader("*");

        // 5. 设置允许的请求方法
        // 允许所有的HTTP方法 (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // 6. 创建 UrlBasedCorsConfigurationSource 对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        // 7. 为所有接口路径 (/**) 应用我们刚刚创建的CORS配置
        source.registerCorsConfiguration("/**", config);

        // 8. 返回一个新的 CorsFilter Bean
        return new CorsFilter(source);
    }
}