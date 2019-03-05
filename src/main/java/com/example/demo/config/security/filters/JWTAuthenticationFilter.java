package com.example.demo.config.security.filters;

import com.example.demo.dao.IUserDAO;
import com.example.demo.config.security.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private final TokenAuthenticationService authenticationService;
    private final IUserDAO userRepo;


    public JWTAuthenticationFilter(TokenAuthenticationService authenticationService, IUserDAO userRepo) {
        this.authenticationService = authenticationService;
        this.userRepo = userRepo;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String email = authenticationService.getUserNameFromToken(request);
            userRepo.findByEmail(email).ifPresent(user -> {
                Authentication authentication = user.getEmail().equals(email)
                        ? authenticationService.getAccessAuthentication(request)
                        : null;
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            LOGGER.debug("JWT has been expired");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "TOKEN_EXPIRED");
        } catch (SignatureException | MalformedJwtException e) {
            LOGGER.debug("JWT has been broken");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "TOKEN_EXPIRED");
        }
    }
}
