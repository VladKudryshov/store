package com.example.demo.models.orders;


import com.example.demo.models.products.Product;
import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    @DBRef(lazy = true)
    @NotNull
    private List<Product> products;
    @NotNull
    private Address address;
    private UserInfo userInfo;
    private Double totalPrice = 0D; // default value

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
