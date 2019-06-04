package com.example.demo.dao;

import com.example.demo.models.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBasketDAO extends JpaRepository<Basket, Integer> {
    Optional<Basket> findBasketByUserId(String userId);

}
