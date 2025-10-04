package com.example.order_system_spring_boot.components.authentication;

import com.example.order_system_spring_boot.components.users.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AuthenticationRepository {

    ResponseEntity<Object> register(User user);
    ResponseEntity<Object> login (Login login , HttpServletRequest request);
    ResponseEntity<Object> logout (HttpServletRequest request);

}
