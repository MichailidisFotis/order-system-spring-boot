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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return ordersRepository.getById(id);
    }


    @GetMapping("/get-order-products/{id}")
    public ResponseEntity<Object> getOrderProducts(@PathVariable String id) {
        return ordersRepository.getOrderProducts(id);
    }

    @GetMapping("/get-order-by-state/{state}")
    public ResponseEntity<Object> getByState(@PathVariable String state) {
        return ordersRepository.getByState(state);
    }


    @PatchMapping("/change-state/{id}")
    public ResponseEntity<Object> changeState(@PathVariable String id){
        return ordersRepository.changeState(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        return ordersRepository.delete(id);
    }

}
