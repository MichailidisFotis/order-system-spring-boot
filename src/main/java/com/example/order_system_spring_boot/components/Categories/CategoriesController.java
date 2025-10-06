package com.example.order_system_spring_boot.components.Categories;


import com.example.order_system_spring_boot.helpers.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesRepository categoriesRepository;

    @GetMapping({"" , "/"})
    public ResponseEntity<Object> getAll(){

        return categoriesRepository.getAll();
    }

    @PostMapping({"" , "/"})
    public ResponseEntity<Object> create(@RequestBody @Validated(Category.OnCreate.class) Category category){
        return categoriesRepository.createCategory(category);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id){
        return categoriesRepository.getById(id);
    }


    @GetMapping("/search")
    public ResponseEntity<Object> getByDescription(@RequestParam String description){
        return categoriesRepository.getByDescription(description);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        return categoriesRepository.delete(id);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated(Category.OnUpdate.class) Category category , @PathVariable String id){
        return categoriesRepository.updateCategory(category , id);
    }





}
