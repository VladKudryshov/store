package com.example.demo.service.impl;

import com.example.demo.service.IAddressService;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IUserService userService;

    @Autowired
    IAddressService addressService;

    @Autowired
    IProductService productService;


}
