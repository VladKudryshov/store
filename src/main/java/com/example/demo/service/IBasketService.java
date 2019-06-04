package com.example.demo.service;

import com.example.demo.models.basket.Basket;

public interface IBasketService {
    void createOrUpdateBasket(String productBasket);

    Basket getBasketByUser();
}
