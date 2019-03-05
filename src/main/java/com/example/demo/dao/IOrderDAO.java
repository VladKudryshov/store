package com.example.demo.dao;

import com.example.demo.models.orders.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IOrderDAO extends MongoRepository<Order, String> {
    Optional<Order> findOrderById(String id);
}
