package com.yls.vocabverse.filter;

import com.yls.vocabverse.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 将这个过滤器声明为一个 Spring Bean，这样才能在 SecurityConfig 中注入它
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // 【关键】注入 UserDetailsService，Spring Security 通过它来加载用户信息
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. 如果请求头中没有 Authorization 信息或不以 "Bearer " 开头，直接放行
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 提取 JWT
        jwt = authHeader.substring(7).trim();



        // 3. 从 JWT 中解析出用户名
        username = jwtUtil.extractUsername(jwt);

        // 4. 如果用户名存在，并且当前 SecurityContext 中没有认证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. 根据用户名加载 UserDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. 验证 JWT 是否有效
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // 7. 如果有效，构建一个认证成功的 Authentication 对象
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // 凭证（密码）我们不需要，因为已经通过 JWT 认证了
                        userDetails.getAuthorities() // 用户的权限信息
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 8. 【核心】将这个 Authentication 对象放入 SecurityContext 中
                //    这样，Spring Security 就知道当前用户已经认证成功了
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("认证成功！用户信息已放入 SecurityContext。用户: " +
                        SecurityContextHolder.getContext().getAuthentication().getName() +
                        ", 权限: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }
        
        // 9. 无论如何，都继续执行过滤器链，让请求到达下一个过滤器或最终的 Controller
        filterChain.doFilter(request, response);
    }
}