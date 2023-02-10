package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByProductName(String productName);

    @Query("select p from Product p where p.productName like %?1% ")
    Page <Product> searchByProductName(String productName , Pageable pageable);
}
