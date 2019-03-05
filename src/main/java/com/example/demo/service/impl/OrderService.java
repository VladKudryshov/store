package com.example.demo.service.impl;

import com.example.demo.dao.IAddressDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.dao.IProductDAO;
import com.example.demo.models.orders.Basket;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.ProductBasket;
import com.example.demo.models.products.Product;
import com.example.demo.models.user.Address;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IProductDAO productDAO;

    @Autowired
    IAddressDAO addressDAO;

    @Autowired
    IUserService userService;

    @Override
    public Order createOrder(Basket basket) {
        Order order = new Order();
        UserInfo userInfo = Optional.ofNullable(userService.getUserInfo())
                .orElse(basket.getUserInfo());
        order.setUserInfo(userInfo);
        order.setAddress(checkAddress(basket.getAddress()));
        checkOrder(basket.getProducts(), order);
        return orderDAO.save(order);
    }

    private Address checkAddress(Address address) {
        if (Objects.isNull(address.getId())) {
            return addressDAO.save(address);
        }
        return addressDAO.findOne(address.getId());
    }

    @Override
    public Order updateOrder(String id, Map<String, Integer> products) {
        Optional<Order> optionalOrder = orderDAO.findOrderById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
//            if (checkOrder(products, order)) return orderDAO.save(order);
        }
        return null;
    }

    private void checkOrder(List<ProductBasket> products, Order order) {
        Set<String> productIds = products
                .stream()
                .map(ProductBasket::getId)
                .collect(Collectors.toSet());

        List<Product> response = productDAO.findByIds(productIds)
                .orElseThrow(RuntimeException::new);

        double sum = response
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
        order.setProducts(response);
        order.setTotalPrice((double) Math.round(sum * 100) / 100);
    }
}
