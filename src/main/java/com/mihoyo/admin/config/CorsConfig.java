package com.mihoyo.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 🌟 极其奔放的放行规则
        config.addAllowedOriginPattern("*"); // 允许所有前端域名跨域访问
        config.setAllowCredentials(true);    // 极其重要：允许前端携带 Authorization 和 Cookie 等凭证！
        config.addAllowedHeader("*");        // 允许所有自定义请求头
        config.addAllowedMethod("*");        // 允许 GET, POST, PUT, DELETE, OPTIONS 等所有方法
        config.setMaxAge(3600L);             // 把浏览器的 OPTIONS 预检请求缓存 1 小时，极大提升性能！

        // 🌟 将这个跨域规则应用到所有的接口路径 (/**) 上
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
