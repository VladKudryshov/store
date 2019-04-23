package com.example.demo.dao;

import com.example.demo.models.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserInfoDAO extends JpaRepository<UserInfo, String> {
    Optional<UserInfo > findByUserId(String userId);
}
