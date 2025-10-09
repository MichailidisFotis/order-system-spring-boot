package com.example.order_system_spring_boot.components.Products;


import com.example.order_system_spring_boot.helpers.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;

    @PostMapping({"", "/"})
    public ResponseEntity<Object> createProduct(@RequestBody @Validated(Product.OnCreate.class) Product product) {
        return productsRepository.createProduct(product);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getAll() {
        return productsRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return productsRepository.getById(id);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated(ValidationGroups.OnUpdate.class) Product product, @PathVariable String id) {
        return productsRepository.updateProduct(product, id);
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> delete(@PathVariable String id){
        return productsRepository.delete(id);
    }


    @PatchMapping("/update-category/{product_id}")
    public ResponseEntity<Object> updateProductCategory(@PathVariable String product_id , @RequestParam String category_id){



        return  productsRepository.changeCategory(product_id , category_id);
    }
}
