package com.example.demo.models.orders;

import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserInfo;

import java.util.List;

public class Basket {
    private Address address;
    private List<ProductBasket> products;
    private UserInfo userInfo;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ProductBasket> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBasket> products) {
        this.products = products;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
