package com.example.demo.dao;

import com.example.demo.models.orders.Order;
import com.example.demo.models.user.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderDAO extends JpaRepository<Order, Integer> {
    Optional<Order> findOrderById(Integer id);
    Page<Order> findAllByUserInfoId(Integer userInfoId, Pageable pageable);
}
