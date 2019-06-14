package com.example.demo.models.orders;

import java.util.List;

public class OrderDTO {
    private List<ProductQuantity> basketProducts;
    private OrderContact orderContact;

    public List<ProductQuantity> getBasketProducts() {
        return basketProducts;
    }

    public void setBasketProducts(List<ProductQuantity> basketProducts) {
        this.basketProducts = basketProducts;
    }

    public OrderContact getOrderContact() {
        return orderContact;
    }

    public void setOrderContact(OrderContact orderContact) {
        this.orderContact = orderContact;
    }
}
