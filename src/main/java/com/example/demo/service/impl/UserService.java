package com.example.demo.service.impl;

import com.example.demo.dao.IUserDAO;
import com.example.demo.dao.IUserInfoDAO;
import com.example.demo.exceptions.user.UserAlreadyExistException;
import com.example.demo.exceptions.user.UserNotAuthenticated;
import com.example.demo.exceptions.user.UserNotFoundException;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IUserInfoDAO userInfoDAO;

    @Override
    public void createUser(UserEntity userEntity) {
        Optional<UserEntity> user = userDAO.findByEmail(userEntity.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistException();
        }
        userEntity.setId(DigestUtils.md5Hex(userEntity.getEmail()));
        userEntity.setPassword(DigestUtils.md5Hex(userEntity.getPassword()));
        userDAO.save(userEntity);
    }

    public UserInfo saveOrUpdateUserInfo(UserInfo userInfo) {
        UserEntity authenticatedUser = getAuthenticatedUser();
        String userId = authenticatedUser.getId();
        userInfoDAO.findByUserId(userId).ifPresent(f -> userInfo.setId(f.getId()));
        userInfo.setUserId(userId);
        return userInfoDAO.save(userInfo);
    }

    public UserInfo getUserInfo() {
        UserEntity authenticatedUser = getAuthenticatedUser();
        return userInfoDAO.findByUserId(authenticatedUser.getId()).orElse(new UserInfo());
    }

    @Override
    public UserInfo createUserInfo(UserInfo userInfo) {
        try {
            getAuthenticatedUser();
            return getUserInfo();
        } catch (UserNotAuthenticated e) {
            return userInfoDAO.save(userInfo);
        }
    }

    @Override
    public List<UserEntity> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public UserInfo getUserInfoById(String id) {
        return userInfoDAO.findByUserId(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity getUserById(String id) {
        return userDAO.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public UserEntity getUserByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public UserEntity getAuthenticatedUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDAO.findById(userId).orElseThrow(UserNotAuthenticated::new);
    }
}
