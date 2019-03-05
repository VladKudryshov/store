package com.example.demo.service;

import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;

public interface IUserService {
    void createUser(UserEntity userEntity);

    Address addAddress(Address address);

    Address changeAddress(String id, Address address);

    UserInfo saveOrUpdateUserInfo(UserInfo userInfo);

    void deleteAddress(String id);

    UserInfo getUserInfo();
}
