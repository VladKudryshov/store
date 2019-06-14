package com.example.demo.models.orders;

import java.util.List;

public class OrderView {
    private OrderContact orderContact;
    private List<ProductOrder> productOrder;
    private Double totalPrice;

    public OrderContact getOrderContact() {
        return orderContact;
    }

    public void setOrderContact(OrderContact orderContact) {
        this.orderContact = orderContact;
    }

    public List<ProductOrder> getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(List<ProductOrder> productOrder) {
        this.productOrder = productOrder;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
