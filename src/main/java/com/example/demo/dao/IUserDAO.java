package com.example.demo.dao;

import com.example.demo.models.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserDAO extends MongoRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String s);
    Optional<UserEntity> findById(String s);
}
