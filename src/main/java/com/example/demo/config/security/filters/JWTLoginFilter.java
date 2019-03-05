package com.example.demo.config.security.filters;

import com.example.demo.dao.IUserDAO;
import com.example.demo.models.user.UserEntity;
import com.example.demo.config.security.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthenticationService authenticationService;
    private IUserDAO userRepo;
    private final ObjectMapper jsonMapper;

    public JWTLoginFilter(String url, AuthenticationManager authManager, TokenAuthenticationService authenticationService, IUserDAO userRepo) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.authenticationService = authenticationService;
        this.userRepo = userRepo;
        this.jsonMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        UserEntity user = jsonMapper.readValue(req.getInputStream(), UserEntity.class);
        String email = user.getEmail();
        String pass = user.getPassword();

        if (Objects.isNull(email)) {
            throw new BadCredentialsException("No client credentials presented");
        }

        if (Objects.isNull(pass)) {
            pass = StringUtils.EMPTY;
        } else {
            pass = DigestUtils.md5Hex(pass);
        }
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(email, pass));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof BadCredentialsException) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The email and password you entered did not match our records.");
        } else {
            super.unsuccessfulAuthentication(request, response, failed);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        userRepo.findByEmail(auth.getName()).ifPresent(user -> {
            try {
                res.setCharacterEncoding("UTF-8");
                res.getWriter().print(new ObjectMapper().writeValueAsString(user));
                res.setStatus(HttpStatus.OK.value());
                authenticationService.addAccessToken(res, user);
                authenticationService.addRefreshToken(res, user);
                user.setLastLogin(new Date());
                userRepo.save(user);
            } catch (IOException e) {
                res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        });
    }
}