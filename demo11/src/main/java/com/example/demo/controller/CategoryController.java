package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.CategoryDto;
import com.example.demo.entity.dto.ProductView;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private ProductService productService;
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
    @GetMapping("/category/product/{id}")
    public ResponseEntity<?> getProductByCategoryId(@PathVariable int id , @RequestParam(name = "page" , defaultValue = "1") int page , @RequestParam(name = "size" , defaultValue = "6") int size){
        List<Product> list = productService.getProductByCategoryId(id , page, size).getContent();
        int pageCount = productService.getProductByCategoryId(id , page, size).getTotalPages();
        ProductView productView = new ProductView();
        productView.setProductList(list);
        productView.setPageCount(pageCount);
        return new ResponseEntity<>(productView,HttpStatus.OK);
    }
}
