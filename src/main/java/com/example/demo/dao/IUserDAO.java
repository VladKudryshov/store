package com.example.demo.dao;

import com.example.demo.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserDAO extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String s);

    Optional<UserEntity> findById(String s);
}
