package com.example.demo.controllers;

import com.example.demo.models.basket.Basket;
import com.example.demo.service.IBasketService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
