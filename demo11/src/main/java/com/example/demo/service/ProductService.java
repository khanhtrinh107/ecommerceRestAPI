package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(int id) throws UserNotFoundException;
    Product create(ProductDto productDto) throws ObjectExistedException;
    Product update(ProductDto productDto , int id) throws ObjectExistedException, UserNotFoundException;
    void delete(int id) throws UserNotFoundException;
}
