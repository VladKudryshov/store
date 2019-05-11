package com.example.demo.service;

import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;

import java.util.List;

public interface IUserService {
    void createUser(UserEntity userEntity);

    UserInfo saveOrUpdateUserInfo(UserInfo userInfo);

    UserEntity getAuthenticatedUser();

    UserInfo createUserInfo(UserInfo userInfo);

    UserInfo getUserInfo();

    List<UserEntity> getUsers();

    UserInfo getUserInfoById(String id);
}
