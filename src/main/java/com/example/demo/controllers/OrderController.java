package com.example.demo.controllers;

import com.example.demo.dao.IOrderDAO;
import com.example.demo.exceptions.user.UserAlreadyExistException;
import com.example.demo.models.orders.Basket;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.Status;
import com.example.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController {

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IOrderService service;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable Integer id) {
        return orderDAO.findOrderById(id).orElseThrow(UserAlreadyExistException::new);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Basket basket) {
        return service.createOrder(basket);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Order updateOrder(@PathVariable Integer id, @RequestBody Map<String, Integer> products) {
        return service.updateOrder(id, products);
    }

    @RequestMapping(value = "{id}/status", method = RequestMethod.PUT)
    public Order changeStatusOrder(@PathVariable Integer id, @RequestBody Status status) {
        return service.changeStatus(id, status);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable Integer id) {
        orderDAO.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Order> getOrders(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size) {
        return service.getOrders(new PageRequest(page, size));
    }


}
