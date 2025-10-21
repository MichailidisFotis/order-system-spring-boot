package com.example.order_system_spring_boot.components.Products;

import com.example.order_system_spring_boot.helpers.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;

public class Product implements ValidationGroups {

    private String id;

    @NotEmpty(message="Description should not be empty" , groups = {OnCreate.class})
    private String description;

//    @NotEmpty(message="Price should not be empty", groups = {OnCreate.class})
    private double price;

    @NotEmpty(message="Brand should not be empty", groups = {OnCreate.class})
    private String brand;


    private String category_id;

    private String dateCreated;

    private String dateModified;


    public Product() {
    }

    public Product(String id, String description, double price, String brand, String category_id, String dateCreated, String dateModified) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.category_id = category_id;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setPrice(float price) {
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


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", category_id='" + category_id + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                '}';
    }
}
