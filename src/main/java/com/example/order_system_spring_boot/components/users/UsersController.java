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


    @GetMapping("")
    public ResponseEntity<Object> getAll(){

        List<User> users =  new ArrayList<User>();


        return ResponseHandler.responseBuilder("Users Retrieved" , HttpStatus.OK , userRepository.getAll(), true);
    }


    @PostMapping("/create-user")
    public ResponseEntity<Object> createUser(@RequestBody @Valid  User user){


        return ResponseHandler.responseBuilder("User created" , HttpStatus.CREATED , userRepository.createUser(user), true);
    }

}
