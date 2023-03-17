package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Product> findAll(int size, int page, String domain , String dir) {
        Sort sort = Sort.by(domain);
        if(dir.equals("asc"))
            sort.ascending();
        else if(dir.equals("desc"))
            sort.descending();
        Pageable pageable = PageRequest.of(page-1,size,sort);
        return productRepository.findAll(pageable);
    }


    @Override
    public Page<Product> searchByProductName(int size, int page, String domain, String dir, String keyword) {
        Sort sort = Sort.by(domain);
        if(dir.equals("asc"))
            sort.ascending();
        else if (dir.equals("desc"))
            sort.descending();
        Pageable pageable1 = PageRequest.of(page-1,size,sort);
        return productRepository.searchByProductName(keyword,pageable1);
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

    @Override
    public List<Object[]> statistics() {
        return productRepository.statitics();
    }

    @Override
    public Page<Product> getSameCategory(int productId , int page , int size ) {
        Pageable pageable = PageRequest.of(page-1,size);
        return productRepository.getSameCategory(productId , pageable);
    }

    @Override
    public List<Product> getLastestProduct(Pageable pageable) {
        return productRepository.getLastestProduct(pageable);
    }

    @Override
    public Page<Product> getProductByCategoryId(int id , int page , int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return productRepository.getProductByCategoryId(id , pageable);
    }

    @Override
    public List<Product> getProductSale() {
        Pageable pageable = PageRequest.of(0,4);
        return productRepository.getProductSale(pageable);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
