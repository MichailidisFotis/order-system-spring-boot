package com.example.order_system_spring_boot.components.Customers;

import com.example.order_system_spring_boot.components.authentication.Login;
import com.example.order_system_spring_boot.components.users.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CustomerRepository {

    ResponseEntity<Object> createCustomer(Customer customer);
    ResponseEntity<Object> getAll ();
    ResponseEntity<Object> getById (String id);
    ResponseEntity<Object> delete(String id);
    ResponseEntity<Object> updateCustomer(Customer customer , String id);

}
