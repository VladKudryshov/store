package com.example.demo.dao;

import com.example.demo.models.orders.OrderContact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderContactDAO extends JpaRepository<OrderContact, Integer>{
    Optional<OrderContact> findOrderContactByIdAndUserId(Integer id, String userId);
}
