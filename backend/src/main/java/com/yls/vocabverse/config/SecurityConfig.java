
package com.yls.vocabverse.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.yls.vocabverse.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring Security 的核心配置类
 * 负责配置认证、授权、会话管理等安全相关的功能
 *
 * @author 杨林森
 */
@Configuration
@EnableWebSecurity // 核心注解，启用 Spring Security 的 Web 安全支持
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 定义需要公开访问的 URL 路径数组。
     * 这些路径将不会被 Spring Security 的认证拦截。
     */
    private static final String[] PUBLIC_URLS = {
            // --- 用户认证相关 ---
            "/api/v1/auth/**",      // 放行所有 /api/v1/auth/ 下的路径 (如: /register, /login)

            // --- Swagger UI 相关 ---
            "/v3/api-docs/**",      // Swagger 的 API JSON 描述文件
            "/swagger-ui/**",       // Swagger UI 页面本身
            "/swagger-ui.html"      // Swagger UI 的主 HTML 文件
    };

    /**
     * 配置密码编码器 (PasswordEncoder) 的 Bean。
     * Spring Security 要求必须为密码进行加密处理。
     *
     * @return BCryptPasswordEncoder 实例，用于密码的加密和比对。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 强哈希算法，这是目前业界推荐的标准
        return new BCryptPasswordEncoder();
    }

    /**
     * 从 AuthenticationConfiguration 中获取 AuthenticationManager
     * 这是 Spring Security 推荐的认证管理器配置方式
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 配置安全过滤器链 (SecurityFilterChain)，这是 Spring Security 3.x 的核心配置入口。
     * 通过链式调用，可以精细化地配置每一个安全细节。
     *
     * @param http HttpSecurity 对象，用于构建安全配置
     * @return 构建好的 SecurityFilterChain
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsFilter corsFilter) throws Exception {
        // --- 核心配置链 ---
        http
                .cors(Customizer.withDefaults())
                // 1. 关闭 CSRF (跨站请求伪造) 防护
                // 原因：我们采用 JWT 进行无状态认证，不依赖于 session 和 cookie，CSRF 攻击的基础不存在。
                .csrf(csrf -> csrf.disable())

                // 2. 配置会话管理策略为“无状态” (STATELESS)
                // 原因：前后端分离架构，服务端不维护用户会话状态，每次请求都依赖于 JWT 进行认证。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. 配置 HTTP 请求的授权规则
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        // 对所有在 PUBLIC_URLS 数组中定义的路径，允许所有用户匿名访问。
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        // 除了上面明确放行的路径，其他任何请求都需要经过认证才能访问。
                        .anyRequest().authenticated()
                );

        // --- TODO: 在这里添加你的 JWT 认证过滤器 ---
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}