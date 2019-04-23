package com.example.demo.config.security;

import com.example.demo.models.user.UserEntity;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ProjectUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public ProjectUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity entity = userService.getUserByEmail(email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(entity.getRole().toString()));
            return new User(entity.getEmail(), entity.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException(email);
        }
    }

}