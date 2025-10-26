package com.example.order_system_spring_boot.components.Orders;

import org.springframework.http.ResponseEntity;

public interface OrdersRepository {



    ResponseEntity<Object> getAll();
    ResponseEntity<Object> getByCustomer(String customer_id);
    ResponseEntity<Object> getByState(String state);
    ResponseEntity<Object> delete(String id);
    ResponseEntity<Object> create(Order order);
    ResponseEntity<Object> changeState(String id);
    ResponseEntity<Object> getById(String id);
    ResponseEntity<Object> getOrderProducts(String id);




}
