package com.example.order_system_spring_boot.components.Orders;

import com.example.order_system_spring_boot.helpers.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;

import java.util.Arrays;

public class Order implements ValidationGroups {
    private String id;


    private Double price;


    @NotEmpty(message = "Customer id should not be empty" , groups = {OnUpdate.class , OnCreate.class})
    private String customer_id;


    private String status;

    @NotEmpty(message = "Payment method should not be empty" , groups = {OnCreate.class})
    private String payment_method;

    private String dateCreated;

    private String dateModified;


    @NotEmpty(message = "Products should not be empty" , groups = {OnCreate.class})
    private OrdersProducts [] products;

    public Order(String id, Double price, String customer_id, String status, String payment_method, String dateCreated, String dateModified , OrdersProducts [] products) {
        this.id = id;
        this.price = price;
        this.customer_id = customer_id;
        this.status = status;
        this.payment_method = payment_method;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.products = products;
    }

    public Order(){};


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public OrdersProducts[] getProducts() {
        return products;
    }

    public void setProducts(OrdersProducts[] products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", customer_id='" + customer_id + '\'' +
                ", status='" + status + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                ", products=" + Arrays.toString(products) +
                '}';
    }
}
