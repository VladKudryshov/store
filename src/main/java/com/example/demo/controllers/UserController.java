package com.example.demo.controllers;

import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public void createUser(@RequestBody UserEntity userEntity) {
        userService.createUser(userEntity);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(@RequestBody UserEntity userEntity) {
        //IGNORE. PROCESSING WITH FILTER
    }

    @RequestMapping(value = "info", method = RequestMethod.POST)
    public void updateUserInfo(@RequestBody UserInfo userInfo) {
        userService.saveOrUpdateUserInfo(userInfo);
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public UserInfo getUserInfo() {
        return userService.getUserInfo();
    }

}
