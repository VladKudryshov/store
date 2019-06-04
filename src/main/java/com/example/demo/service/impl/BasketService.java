package com.example.demo.service.impl;

import com.example.demo.dao.IBasketDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.exceptions.basket.BasketNotFoundException;
import com.example.demo.models.basket.Basket;
import com.example.demo.models.orders.ProductBasket;
import com.example.demo.service.IAddressService;
import com.example.demo.service.IBasketService;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService implements IBasketService {

    @Autowired
    IBasketDAO basketDAO;

    @Autowired
    IUserService userService;

    @Override
    public void createOrUpdateBasket(String productsJson) {
        String userId = userService.getAuthenticatedUser().getId();
        Basket basket = basketDAO.findBasketByUserId(userId).orElse(new Basket(userId));
        basket.setProducts(productsJson);
        basketDAO.save(basket);
    }

    @Override
    public Basket getBasketByUser() {
        String userId = userService.getAuthenticatedUser().getId();
        return basketDAO.findBasketByUserId(userId).orElseThrow(BasketNotFoundException::new);
    }
}
