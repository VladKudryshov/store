package com.example.demo.controllers;

import com.example.demo.exceptions.address.InvalidAddressException;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.OrderDTO;
import com.example.demo.models.orders.OrderTableView;
import com.example.demo.models.orders.OrderView;
import com.example.demo.models.user.Address;
import com.example.demo.service.IAddressService;
import com.example.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createOrder(@RequestBody OrderDTO dto) {
        orderService.createOrder(dto);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Order> getUserOrders() {
        return orderService.getUserOrders();
    }

    @RequestMapping(value = "table", method = RequestMethod.GET)
    public List<OrderTableView> getOrdersSimpleList() {
        return orderService.getSimpleOrders();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public OrderView getUserOrderById(@PathVariable Integer id) {
        return orderService.getUserOrderById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteOrderById(@PathVariable Integer id) {
        orderService.deleteOrderById(id);
    }

    @ExceptionHandler
    public String handleValidateExceptions(InvalidAddressException ex){
        return ex.getMessage();
    }


}
