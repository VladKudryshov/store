package com.example.demo.service.impl;

import com.example.demo.dao.IOrderContactDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.dao.IOrderDetailsDAO;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.OrderContact;
import com.example.demo.models.orders.OrderDTO;
import com.example.demo.models.orders.OrderDetails;
import com.example.demo.models.orders.OrderView;
import com.example.demo.models.orders.ProductOrder;
import com.example.demo.models.orders.ProductQuantity;
import com.example.demo.models.products.Product;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IUserService userService;

    @Autowired
    IOrderContactDAO orderContactDAO;

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IOrderDetailsDAO orderDetailsDAO;

    @Autowired
    IProductService productService;

    @Override
    public List<Order> getUserOrders() {
        String userId = userService.getAuthenticatedUser().getId();
        return orderDAO.findAllByUserId(userId);
    }

    @Override
    public OrderView getUserOrderById(Integer id) {
        String userId = userService.getAuthenticatedUser().getId();
        Order order = orderDAO.findOrderByIdAndUserId(id, userId);
        OrderContact orderContact = orderContactDAO.findOne(order.getOrderContactId());
        List<OrderDetails> orderDetails = orderDetailsDAO.findOrderDetailsByOrderId(id);

        Map<Integer, List<OrderDetails>> collect = orderDetails.stream().collect(Collectors.groupingBy(OrderDetails::getProductId));

        Set<Integer> productIds = collect.keySet();
        List<Product> productsByIds = productService.getProductsByIds(productIds);

        OrderView orderView = new OrderView();
        orderView.setOrderContact(orderContact);
        List<ProductOrder> collect1 = productsByIds
                .stream()
                .map(f -> {
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setId(f.getId());
                    productOrder.setImage(f.getImage());
                    productOrder.setName(f.getName());
                    productOrder.setCategory(f.getCategory());
                    productOrder.setPrice(f.getPrice());
                    productOrder.setDiscount(f.getDiscount());

                    OrderDetails details = collect.get(f.getId()).get(0);
                    productOrder.setQuantity(details.getQuantity());
                    productOrder.setTotalPrice(details.getTotalPrice());
                    return productOrder;
                })
                .collect(Collectors.toList());
        orderView.setProductOrder(collect1);
        orderView.setOrderStatus(order.getStatus());
        orderView.setTotalPrice(collect1.stream().mapToDouble(ProductOrder::getTotalPrice).sum());
        return orderView;
    }

    @Override
    @Transactional
    public void createOrder(OrderDTO dto) {
        String userId = userService.getAuthenticatedUser().getId();
        Map<Integer, Double> pr = dto.getBasketProducts().stream().collect(Collectors.toMap(ProductQuantity::getId, ProductQuantity::getQuantity));

        OrderContact tempOrderContact = dto.getOrderContact();
        tempOrderContact.setUserId(userId);

        OrderContact orderContact = orderContactDAO.findOrderContactByIdAndUserId(tempOrderContact.getId(), userId).orElse(orderContactDAO.save(tempOrderContact));

        Order order = new Order();
        order.setOrderContactId(orderContact.getId());
        order.setUserId(userId);
        order.setStatus("PENDING");
        Order save = orderDAO.save(order);

        List<OrderDetails> orderDetails = productService.getProductsByIds(pr.keySet())
                .stream()
                .map(f -> {
                    OrderDetails orderDetail = new OrderDetails();
                    orderDetail.setProductId(f.getId());
                    orderDetail.setOrderId(save.getId());
                    Double quantity = pr.get(f.getId());
                    orderDetail.setQuantity(quantity);
                    orderDetail.setTotalPrice(f.getPriceWithDiscount() * quantity);
                    return orderDetail;
                })
                .collect(Collectors.toList());
        orderDetailsDAO.save(orderDetails);

    }

}
