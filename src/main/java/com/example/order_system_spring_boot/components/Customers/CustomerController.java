package com.example.order_system_spring_boot.components.Customers;

import jakarta.validation.Valid;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;


    @PostMapping({"/", ""})
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid Customer customer) {

        return customerRepository.createCustomer(customer);
    }


    @GetMapping({"/", ""})
    public ResponseEntity<Object> getAll() {

        return customerRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return customerRepository.getById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return customerRepository.updateCustomer(customer, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable String id) {
        return customerRepository.delete(id);
    }



}
