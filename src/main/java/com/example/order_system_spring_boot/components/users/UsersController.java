package com.example.order_system_spring_boot.components.users;

import com.example.order_system_spring_boot.helpers.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;


    @GetMapping({"","/"})
    public ResponseEntity<Object> getAll() {

        return userRepository.getAll();
    }



}
