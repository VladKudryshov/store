package com.example.demo.models.orders;

import java.util.List;

public class OrderDTO {
    private List<ProductQuantity> basket;
    private OrderContact contact;

    public List<ProductQuantity> getBasket() {
        return basket;
    }

    public void setBasket(List<ProductQuantity> basket) {
        this.basket = basket;
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }
}
