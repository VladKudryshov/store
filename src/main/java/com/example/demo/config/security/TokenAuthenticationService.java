package com.example.demo.config.security;

import com.example.demo.models.user.UserEntity;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashSet;

@Service
public class TokenAuthenticationService {

    private TokenConfig tokenConfig;

    public void addAccessToken(HttpServletResponse res, UserEntity entity) {
        res.addHeader(Tokens.ACCESS_TOKEN.getHeader(), TokenConfig.TOKEN_PREFIX + " " + tokenConfig.buildToken(entity, tokenConfig.getAccessTokenExpirationTime(), Tokens.ACCESS_TOKEN.getType()));
    }

    public void addRefreshToken(HttpServletResponse res, UserEntity entity) {
        res.addHeader(Tokens.REFRESH_TOKEN.getHeader(), TokenConfig.TOKEN_PREFIX + " " + tokenConfig.buildToken(entity, tokenConfig.getRefreshTokenExpirationTime(), Tokens.REFRESH_TOKEN.getType()));
    }

    public Authentication getAccessAuthentication(ServletRequest request) {
        String token = ((HttpServletRequest) request).getHeader(Tokens.ACCESS_TOKEN.getHeader());
        return parseToken(token, Tokens.ACCESS_TOKEN.getType());
    }

    public Authentication getAccessAuthenticationWebSocket(ServletRequest request) {
//        MessageHeaderAccessor.getAccessor(new )
        String token = ((HttpServletRequest) request).getHeader(Tokens.ACCESS_TOKEN.getHeader());
        return parseToken(token, Tokens.ACCESS_TOKEN.getType());
    }

    public Authentication getRefreshAuthentication(ServletRequest request) {
        String token = ((HttpServletRequest) request).getHeader(Tokens.REFRESH_TOKEN.getHeader());
        Authentication auth = parseToken(token, Tokens.REFRESH_TOKEN.getType());
        if (auth == null) {
            throw new RuntimeException();
        }
        return auth;
    }

    public String getUserNameFromToken(final ServletRequest request) {
        String token = ((HttpServletRequest) request).getHeader(Tokens.ACCESS_TOKEN.getHeader());
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return Jwts.parser().setSigningKey(tokenConfig.getSecret()).parseClaimsJws(token.replace(TokenConfig.TOKEN_PREFIX, "")).getBody().getSubject();
    }

    private Authentication parseToken(String token, String type) {
        if (token != null && !token.trim().isEmpty()) {
            String userId = Jwts.parser().setSigningKey(tokenConfig.getSecret()).parseClaimsJws(token.replace(TokenConfig.TOKEN_PREFIX, "")).getBody().getId();
            String role = Jwts.parser().setSigningKey(tokenConfig.getSecret()).parseClaimsJws(token.replace(TokenConfig.TOKEN_PREFIX, "")).getHeader().get("role").toString();
            String tokenType = Jwts.parser().setSigningKey(tokenConfig.getSecret()).parseClaimsJws(token.replace(TokenConfig.TOKEN_PREFIX, "")).getHeader().get("type").toString();
            return getAuthentication(type, userId, role, tokenType);
        }
        return null;
    }

    public Authentication getAuthentication(String type, String userId, String role, String tokenType) {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return userId != null && tokenType.equals(type) ? new UsernamePasswordAuthenticationToken(userId, null, authorities) : null;
    }

    @Autowired
    public void setTokenConfig(final TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }
}
