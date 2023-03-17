package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByProductName(String productName);

    @Query("select p from Product p where p.productName like %?1% ")
    Page <Product> searchByProductName(String productName , Pageable pageable);

    @Query("select p.productName , sum(o.quantity) , sum(o.initPrice*o.quantity) from Product p inner join OrderDetail o on p.productId = o.product.productId group by p.productId order by sum(o.initPrice*o.quantity) desc ")
    List<Object[]> statitics();

    @Query("select p from Product p where p.category.categoryId = ?1")
    Page<Product> getSameCategory(int productId , Pageable pageable);

    @Query("select p from Product p order by p.productId  desc  ")
    List<Product> getLastestProduct(Pageable pageable);

    @Query("select p from Product p where p.category.categoryId = ?1")
    Page<Product> getProductByCategoryId(int id , Pageable pageable);

    @Query("select p from Product p order by p.productId")
    List<Product> getProductSale(Pageable pageable);
}
