package com.example.demo.models.orders;

import com.example.demo.models.products.Product;

public class ProductOrder extends Product {
    private Double quantity;
    private Double totalPrice;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
