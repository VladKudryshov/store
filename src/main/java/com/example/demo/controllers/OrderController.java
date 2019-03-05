package com.example.demo.controllers;

import com.example.demo.dao.IOrderDAO;
import com.example.demo.exceptions.UserAlreadyExistException;
import com.example.demo.models.orders.Basket;
import com.example.demo.models.orders.Order;
import com.example.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IOrderService service;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable String id) {
        return orderDAO.findOrderById(id).orElseThrow(UserAlreadyExistException::new);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Basket basket) {
        return service.createOrder(basket);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Order updateOrder(@PathVariable String id, @RequestBody Map<String, Integer> products) {
        return service.updateOrder(id, products);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable String id) {
        orderDAO.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Order> getOrders(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size) {
        return orderDAO.findAll(new PageRequest(page, size));
    }


}
