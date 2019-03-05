package com.example.demo.models.orders;

public class ProductBasket {
    private String id;
    private Integer quantity = 1; //default value

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
