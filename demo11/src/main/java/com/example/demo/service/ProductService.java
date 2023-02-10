package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<Product> findAll(int size , int page , String domain , String dir);
    Product findById(int id) throws UserNotFoundException;
    Product create(ProductDto productDto) throws ObjectExistedException;
    Product update(ProductDto productDto , int id) throws ObjectExistedException, UserNotFoundException;
    void delete(int id) throws UserNotFoundException;
}
