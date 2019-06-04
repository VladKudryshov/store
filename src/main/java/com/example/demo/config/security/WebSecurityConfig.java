package com.example.demo.config.security;

import com.example.demo.config.security.filters.JWTAuthenticationFilter;
import com.example.demo.config.security.filters.JWTLoginFilter;
import com.example.demo.config.security.filters.JWTRefreshFilter;
import com.example.demo.dao.IUserDAO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenAuthenticationService authenticationService;
    private final IUserDAO userRepo;
    private final Boolean isEnabled;

    @Autowired
    public WebSecurityConfig(
            UserDetailsService userDetailsService,
            TokenAuthenticationService authenticationService,
            IUserDAO userRepo,
            @Value("${security.enabled}") Boolean isEnabled) {
        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
        this.userRepo = userRepo;
        this.isEnabled = isEnabled;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (isEnabled) {
            http
                    .cors()
                    .and()
                    .csrf().disable().authorizeRequests()
                    .antMatchers("/api/users/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/users/registration").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/users/token").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/products/cart").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/orders").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .and()
                    .addFilterBefore(new JWTAuthenticationFilter(authenticationService, userRepo), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JWTRefreshFilter("/api/users/token", authenticationService, userRepo), JWTAuthenticationFilter.class)
                    .addFilterBefore(new JWTLoginFilter("/api/users/login", authenticationManager(), authenticationService, userRepo),
                            JWTRefreshFilter.class);
        } else {

            http
                    .cors()
                    .and()
                    .csrf().disable().authorizeRequests()
                    .antMatchers("/**").permitAll();
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Accept-Charset", "Authorization", "Origin", "Accept", "User-Agent"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}

