package com.example.demo.service.impl;

import com.example.demo.dao.IAddressDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.dao.IProductDAO;
import com.example.demo.exceptions.order.OrderNotFoundException;
import com.example.demo.models.orders.*;
import com.example.demo.models.products.Product;
import com.example.demo.models.user.Address;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.UserEntity;
import com.example.demo.service.IAddressService;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IUserService userService;

    @Autowired
    IAddressService addressService;

    @Autowired
    IProductService productService;

    @Override
    public Order createOrder(Basket basket) {
        Order order = new Order();
        order.setUserInfo(userService.createUserInfo(basket.getUserInfo()));
        order.setAddress(addressService.chooseAddress(basket.getAddress()));
        order.setStatus(Status.CREATED);

        Map<String, Integer> productNames = basket.getProducts();
        productService.getProductsByNames(productNames.keySet(), products -> {
            if(productNames.size() != products.size()){
                throw new RuntimeException("Neverniy nabor data");
            }
            order.setProducts(products
                    .stream()
                    .map(product -> {
                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setProductId(product.getId());
                        orderProduct.setPrice(product.getPrice());
                        Integer quantity = productNames.get(product.getName());
                        orderProduct.setQuantity(quantity);
                        orderProduct.setTotalPrice();
                        return orderProduct;
                    })
                    .collect(Collectors.toSet()));
        });

        return orderDAO.save(order);
    }

    @Override
    public Page<Order> getOrders(PageRequest pageRequest) {
        UserEntity authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser.getRole().equals(Role.ADMIN)) {
            return orderDAO.findAll(pageRequest);
        }
        return orderDAO.findAllByUserInfoId(userService.getUserInfo().getId(), pageRequest);
    }

    @Override
    public Order updateOrder(Integer id, Map<String, Integer> products) {
        Optional<Order> optionalOrder = orderDAO.findOrderById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
        }
        return null;
    }

    @Override
    public Order changeStatus(Integer id, Status status) {
        Order order = orderDAO.findOrderById(id).orElseThrow(OrderNotFoundException::new);
        order.setStatus(status);
        return orderDAO.save(order);
    }
}
