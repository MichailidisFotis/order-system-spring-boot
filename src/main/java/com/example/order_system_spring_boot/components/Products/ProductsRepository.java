package com.example.order_system_spring_boot.components.Products;

import com.example.order_system_spring_boot.components.Customers.Customer;
import org.springframework.http.ResponseEntity;

public interface ProductsRepository {

    ResponseEntity<Object> createProduct(Product product);
    ResponseEntity<Object> getAll ();
    ResponseEntity<Object> getById (String id);
    ResponseEntity<Object> delete(String id);
    ResponseEntity<Object> updateProduct(Product product , String id);
    ResponseEntity<Object> changeCategory(String product_id , String categoryId);


}
