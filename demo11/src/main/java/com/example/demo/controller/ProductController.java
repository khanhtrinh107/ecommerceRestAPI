package com.example.demo.controller;

import com.example.demo.entity.Product;
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

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product")
    public ResponseEntity<?> getProducts(@RequestParam(name = "page") int page , @RequestParam(name = "size") int size , @RequestParam(name = "domain") String domain , @RequestParam(name = "dir") String dir){
        List<Product> list = productService.findAll(size,page,domain,dir).getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/product/search")
    public ResponseEntity<?> searchProduct(@RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" ,required = false , defaultValue = "5") int size,@RequestParam(name = "domain" , required = false , defaultValue = "productName") String domain , @RequestParam(name = "dir" ,required = false , defaultValue = "asc") String dir , @RequestParam(name = "keyword" ,required = false , defaultValue = "") String keyword){
        List<Product> list = productService.searchByProductName(size,page,domain,dir,keyword).getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
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
