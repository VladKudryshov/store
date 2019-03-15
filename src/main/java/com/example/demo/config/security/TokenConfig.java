package com.example.demo.config.security;

import com.example.demo.models.user.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenConfig {
    public static final String TOKEN_PREFIX = "Bearer";

    @Value("1001000")
    private long accessTokenExpirationTime;
    @Value("2000000")
    private long refreshTokenExpirationTime;
    @Value("asdasfasf")
    private String secret;

    public String buildToken(UserEntity entity, long expirationTime, String type) {
        return Jwts.builder().setId(entity.getId())
                .setSubject(entity.getEmail())
                .setHeaderParam("role", entity.getRole().toString())
                .setHeaderParam("type", type)
                .signWith(SignatureAlgorithm.HS512, getSecret()).compact();
    }

    public long getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public long getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }

    public String getSecret() {
        return secret;
    }
}
