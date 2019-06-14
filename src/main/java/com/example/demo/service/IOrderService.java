package com.example.demo.service;

import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.OrderDTO;
import com.example.demo.models.orders.OrderView;

import java.util.List;

public interface IOrderService {
    void createOrder(OrderDTO dto);

    List<Order> getUserOrders();

    OrderView getUserOrderById(Integer id);
}
