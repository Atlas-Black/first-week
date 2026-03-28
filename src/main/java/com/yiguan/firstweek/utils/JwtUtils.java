package com.yiguan.firstweek.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtils {

    //JWT的签名密钥，生成Token时签名；解析Token时验签
    private static final String SECRET_KEY_STR = "yiguan-first-week-secret-key-123456";
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET_KEY_STR.getBytes(StandardCharsets.UTF_8));
    //Token的过期时间，24h
    private static final long EXPIRE_TIME = 1000L * 60 * 60 * 24;

    public static String createToken(Long userId) {     //根据用户id生成JWT字符串
        return Jwts.builder()
                .setSubject("login-user")
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {     //把前端传来的 Token 解析出来，并拿到里面存的用户信息
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}