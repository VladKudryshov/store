package com.example.demo.controllers;

import com.example.demo.exceptions.address.InvalidAddressException;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.OrderDTO;
import com.example.demo.models.orders.OrderTableView;
import com.example.demo.models.orders.OrderView;
import com.example.demo.service.IOrderService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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
    @SendTo("/notification")
    public String deleteOrderById(@PathVariable Integer id) {
        orderService.deleteOrderById(id);
        return "Deleted";
    }

    @ExceptionHandler
    public String handleValidateExceptions(InvalidAddressException ex){
        return ex.getMessage();
    }


}
