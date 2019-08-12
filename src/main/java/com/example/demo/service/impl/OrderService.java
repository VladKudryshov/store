package com.example.demo.service.impl;

import com.example.demo.dao.IOrderContactDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.dao.IOrderDetailsDAO;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.OrderContact;
import com.example.demo.models.orders.OrderDTO;
import com.example.demo.models.orders.OrderDetails;
import com.example.demo.models.orders.OrderTableView;
import com.example.demo.models.orders.OrderView;
import com.example.demo.models.orders.ProductOrder;
import com.example.demo.models.orders.ProductQuantity;
import com.example.demo.models.products.Product;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> getUserOrders() {
        String userId = userService.getAuthenticatedUser().getId();
        return orderDAO.findAllByUserId(userId);
    }

    @Override
    public List<OrderTableView> getSimpleOrders() {
        return jdbcTemplate.query("SELECT rd.id, user_name, user_second_name, created, updated, status, cost, count(od.id) products_count " +
                "FROM order_details od " +
                "INNER JOIN orders rd ON od.order_id = rd.id " +
                "INNER JOIN order_contacts oc ON rd.order_contact_id = oc.id " +
                "GROUP BY 1,2,3,4,5,6,7 " +
                "ORDER BY updated DESC ", (resultSet, i) -> {
            OrderTableView orderTableView = new OrderTableView();
            orderTableView.setOrderId(resultSet.getInt("id"));
            orderTableView.setUserName(resultSet.getString("user_name"));
            orderTableView.setUserSecondName(resultSet.getString("user_second_name"));
            orderTableView.setCreated(resultSet.getDate("created"));
            orderTableView.setUpdated(resultSet.getDate("updated"));
            orderTableView.setCost(resultSet.getDouble("cost"));
            orderTableView.setStatus(resultSet.getString("status"));
            orderTableView.setProductsCount(resultSet.getInt("products_count"));
            return orderTableView;
        });
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

        OrderContact orderContact = orderContactDAO.findOrderContactByIdAndUserId(tempOrderContact.getId(), userId)
                .orElse(orderContactDAO.save(tempOrderContact));

        Order order = orderDAO.save(new Order());

        List<OrderDetails> orderDetails = productService.getProductsByIds(pr.keySet())
                .stream()
                .map(f -> {
                    OrderDetails orderDetail = new OrderDetails();
                    orderDetail.setProductId(f.getId());
                    orderDetail.setOrderId(order.getId());
                    Double quantity = pr.get(f.getId());
                    orderDetail.setQuantity(quantity);
                    orderDetail.setTotalPrice(f.getPriceWithDiscount() * quantity);
                    return orderDetail;
                })
                .collect(Collectors.toList());
        orderDetailsDAO.save(orderDetails);
        double sum = orderDetails.stream().mapToDouble(OrderDetails::getTotalPrice).sum();

        order.setOrderContactId(orderContact.getId());
        order.setUserId(userId);
        order.setStatus("PENDING");
        order.setCost(sum);
        orderDAO.save(order);
    }

}
