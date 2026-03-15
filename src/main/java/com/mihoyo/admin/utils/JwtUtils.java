package com.mihoyo.admin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "WordKillerAdminSecretKeyWordKillerAdminSecretKey";

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public static String generateToken(String loginAccount, String role,String id) {
        return Jwts.builder()
                .claim("loginAccount",loginAccount)
                .claim("role",role)
                .claim("id",id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)

                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
