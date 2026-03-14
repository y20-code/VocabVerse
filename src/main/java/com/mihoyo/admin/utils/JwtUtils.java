package com.mihoyo.admin.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "WordKillerAdminSecretKeyWordKillerAdminSecretKey";

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public static String generateToken(String loginAccount, String role) {
        return Jwts.builder()
                .claim("loginAccount",loginAccount)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                .signWith(SignatureAlgorithm.ES256,SECRET_KEY)

                .compact();
    }
}
