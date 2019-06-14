package com.example.demo.dao;

import com.example.demo.models.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDAO extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(String userId);

    Order findOrderByIdAndUserId(Integer id, String userId);

}
