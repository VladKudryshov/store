package com.example.demo.config.security.filters;

import com.example.demo.dao.IUserDAO;
import com.example.demo.models.user.UserEntity;
import com.example.demo.config.security.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTRefreshFilter extends GenericFilterBean {

    private TokenAuthenticationService authenticationService;
    private IUserDAO userRepo;
    private RequestMatcher requestMatcher;

    private final static Logger LOGGER = LoggerFactory.getLogger(JWTRefreshFilter.class);

    public JWTRefreshFilter(String url, TokenAuthenticationService authenticationService, IUserDAO userRepo) {
        this.authenticationService = authenticationService;
        this.userRepo = userRepo;
        requestMatcher = new AntPathRequestMatcher(url, HttpMethod.GET.toString());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (requestMatcher.matches((HttpServletRequest) request)) {
            try {
                Authentication authentication = authenticationService.getRefreshAuthentication(request);
                UserEntity entity = new UserEntity();
                authenticationService.addAccessToken((HttpServletResponse) response, entity);
            } catch (ExpiredJwtException e) {
                LOGGER.debug("JWT has been expired");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "REFRESH_TOKEN_EXPIRED");
            } catch (SignatureException | MalformedJwtException e) {
                LOGGER.debug("JWT has been broken");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "REFRESH_TOKEN_EXPIRED");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
