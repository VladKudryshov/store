package com.example.demo.dao;

import com.example.demo.models.orders.OrderContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderContactDAO extends JpaRepository<OrderContact, Integer>{
    Optional<OrderContact> findOrderContactByIdAndUserId(Integer id, String userId);
    List<OrderContact> findOrderContactsByUserId(String userId);
}
