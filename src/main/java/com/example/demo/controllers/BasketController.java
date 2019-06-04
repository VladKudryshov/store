package com.example.demo.controllers;

import com.example.demo.dao.IBasketDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.exceptions.user.UserAlreadyExistException;
import com.example.demo.models.basket.Basket;
import com.example.demo.models.orders.ProductBasket;
import com.example.demo.models.orders.Status;
import com.example.demo.service.IBasketService;
import com.example.demo.service.IOrderService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "api/basket")
public class BasketController {

    @Autowired
    IBasketService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Basket getBasket() {
       return service.getBasketByUser();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void updateBasket(@RequestBody JsonNode basket) {
        service.createOrUpdateBasket(basket.toString());
    }

}
