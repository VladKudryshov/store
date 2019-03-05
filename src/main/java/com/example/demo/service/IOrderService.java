package com.example.demo.service;

import com.example.demo.models.orders.Basket;
import com.example.demo.models.orders.Order;

import java.util.Map;

public interface IOrderService {
    Order createOrder(Basket basket);

    Order updateOrder(String id, Map<String, Integer> products);
}
