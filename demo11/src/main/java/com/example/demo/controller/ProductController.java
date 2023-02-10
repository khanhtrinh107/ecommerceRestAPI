package com.example.demo.controller;

import com.example.demo.entity.dto.ProductDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(productService.findAll() , HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDto productDto) throws ObjectExistedException {
        return new ResponseEntity<>(productService.create(productDto) , HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDto productDto , @PathVariable int id) throws UserNotFoundException, ObjectExistedException {
        return  new ResponseEntity<>(productService.update(productDto,id) , HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id ){
        try{
            productService.delete(id);
            return new ResponseEntity<>("Da Xoa Thanh Cong " , HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>("Co loi xay ra" , HttpStatus.BAD_REQUEST);
        }

    }
}
