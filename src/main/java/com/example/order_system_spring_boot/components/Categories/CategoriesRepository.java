package com.example.order_system_spring_boot.components.Categories;

import org.springframework.http.ResponseEntity;

public interface CategoriesRepository {


    ResponseEntity<Object> createCategory(Category category);
    ResponseEntity<Object> delete(String id);
    ResponseEntity<Object> updateCategory(Category category , String id);
    ResponseEntity<Object> getAll();
    ResponseEntity<Object> getByDescription(String description);
    ResponseEntity<Object> getById(String id);

}
