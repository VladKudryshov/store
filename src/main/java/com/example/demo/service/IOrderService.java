package com.example.demo.service;

import com.example.demo.models.orders.Basket;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface IOrderService {
    Order createOrder(Basket basket);

    Order updateOrder(Integer id, Map<String, Integer> products);

    Page<Order> getOrders(PageRequest pageRequest);

    Order changeStatus(Integer id, Status status);
}
