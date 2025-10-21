package com.example.order_system_spring_boot.components.Orders;

import com.example.order_system_spring_boot.helpers.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;

public class OrdersProducts {

    private String product_id;

    private String order_id;

    private int quantity;

    private String description;

    private double price;

    private String brand;

    private String category_id;

    public OrdersProducts(){};

    public OrdersProducts(String product_id, String order_id, int quantity, String description, double price, String brand, String category_id) {
        this.product_id = product_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.category_id = category_id;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
