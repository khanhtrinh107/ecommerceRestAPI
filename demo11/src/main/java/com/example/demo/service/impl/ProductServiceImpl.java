package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) throws UserNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No product have id " + id));
    }

    @Override
    public Product create(ProductDto productDto) throws ObjectExistedException {
        if(!ObjectUtils.isEmpty(productRepository.findByProductName(productDto.getProductName()))){
            throw new ObjectExistedException("Product existed!");
        }
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());
        product.setCategory(categoryRepository.findByCategoryName(productDto.getCategory()));
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductDto productDto, int id) throws ObjectExistedException, UserNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No product have id " + id));
        if(!ObjectUtils.isEmpty(productRepository.findByProductName(productDto.getProductName()))){
            throw new ObjectExistedException("Product existed!");
        }
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());
        product.setCategory(categoryRepository.findByCategoryName(productDto.getCategory()));
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Produt have id " + id));
        productRepository.deleteById(id);
    }
}
