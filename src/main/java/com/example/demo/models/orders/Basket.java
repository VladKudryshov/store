package com.example.demo.models.orders;

import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserInfo;

import java.util.List;
import java.util.Map;

public class Basket {
    private Map<String,Integer> products;
    private UserInfo userInfo;
    private Address address;

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
