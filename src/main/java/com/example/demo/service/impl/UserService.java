package com.example.demo.service.impl;

import com.example.demo.dao.IAddressDAO;
import com.example.demo.dao.IUserDAO;
import com.example.demo.dao.IUserInfoDAO;
import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IAddressDAO addressDAO;

    @Autowired
    private IUserInfoDAO userInfoDAO;

    @Override
    public void createUser(UserEntity userEntity) {
        userEntity.setId(DigestUtils.md5Hex(userEntity.getEmail()));
        userEntity.setPassword(DigestUtils.md5Hex(userEntity.getPassword()));
        userDAO.save(userEntity);
    }

    @Override
    public Address addAddress(Address address) {
        return addressDAO.save(address);
    }

    @Override
    public Address changeAddress(String id, Address address) {
        Address oldAddress = addressDAO.findOne(id);
        if (Objects.nonNull(oldAddress)) {
            return addressDAO.save(address);
        }
        throw new RuntimeException();
    }

    public UserInfo saveOrUpdateUserInfo(UserInfo userInfo) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInfo byUserUID = userInfoDAO.findByUserUID(name);
        if(Objects.nonNull(byUserUID)){
            userInfo.setId(byUserUID.getId());
        }
        userInfo.setUserUID(name);
        return userInfoDAO.save(userInfo);
    }

    public UserInfo getUserInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userInfoDAO.findByUserUID(name);
    }


    @Override
    public void deleteAddress(String id) {
        Address address = addressDAO.findOne(id);
        if (Objects.nonNull(address)){
            addressDAO.delete(address);
        }
    }

    public UserEntity getUserByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(RuntimeException::new);
    }

}
