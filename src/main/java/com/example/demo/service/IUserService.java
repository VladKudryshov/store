package com.example.demo.service;

import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;

public interface IUserService {
    void createUser(UserEntity userEntity);

    UserInfo saveOrUpdateUserInfo(UserInfo userInfo);

    UserEntity getAuthenticatedUser();

    UserInfo createUserInfo(UserInfo userInfo);

    UserInfo getUserInfo();
}
