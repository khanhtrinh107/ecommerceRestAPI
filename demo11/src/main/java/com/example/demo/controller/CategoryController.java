package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.dto.CategoryDto;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category")
    public ResponseEntity<?> getCategories(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.create(categoryDto) , HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDto categoryDto , @PathVariable int id){
        categoryService.delete(id);
        return new ResponseEntity<>("Da Xoa thanh cong" , HttpStatus.OK);
    }
}
