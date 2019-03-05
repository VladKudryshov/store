package com.example.demo.dao;

import com.example.demo.models.user.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserInfoDAO extends MongoRepository<UserInfo, String> {
    UserInfo findByUserUID(String userUID);
}
