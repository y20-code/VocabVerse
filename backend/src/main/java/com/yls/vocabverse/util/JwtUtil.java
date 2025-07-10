package com.yls.vocabverse.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // 从 application.yml 配置文件中读取密钥
    @Value("${jwt.secret}")
    private String secret;

    // 从 application.yml 配置文件中读取过期时间（毫秒）
    @Value("${jwt.expiration}")
    private Long expiration;

    private Key key;

    // 在构造函数后执行，用于生成密钥实例
    @javax.annotation.PostConstruct
    public void init() {
        // 使用 HMAC-SHA256 算法生成一个安全的密钥
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 从 Token 中提取所有声明 (claims)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 从 Token 中提取单个声明
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从 Token 中提取过期日期
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 检查 Token 是否已过期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 生成 JWT Token
     * @param userId 用户ID
     * @param username 用户名
     * @param role 角色
     * @return 生成的 Token
     */
    public String generateToken(Long userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        // 你可以在这里放入任何想包含在 Token 中的自定义信息
        claims.put("userId", userId);

        claims.put("role", role);
        return createToken(claims, username);
    }

    // 根据声明和主题（用户名）创建 Token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // 设置自定义声明
                .setSubject(subject) // 设置主题（通常是用户名）
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
                .signWith(key, SignatureAlgorithm.HS256) // 使用 HS256 签名算法和密钥
                .compact();
    }

    /**
     * 验证 Token 是否有效
     * @param token 客户端传入的 Token
     * @param username 从数据库中查出的用户名（用于比对）
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        // 检查 Token 中的用户名是否与期望的用户名匹配，并且 Token 没有过期
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}