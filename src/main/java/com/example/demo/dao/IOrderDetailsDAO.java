package com.example.demo.dao;

import com.example.demo.models.orders.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailsDAO extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findOrderDetailsByOrderId(Integer id);
}
