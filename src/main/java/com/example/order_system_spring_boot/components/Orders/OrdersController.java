package com.example.order_system_spring_boot.components.Orders;


import com.example.order_system_spring_boot.helpers.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersRepository ordersRepository;


    @PostMapping({"" , "/"})
    ResponseEntity<Object> create(@RequestBody @Validated(Order.OnCreate.class) Order order){
        return ordersRepository.create(order);
    }


    @GetMapping({"" , "/"})
    ResponseEntity<Object> getAll(){
        return ordersRepository.getAll();
    }









}
