package com.example.demo.entity.dto;

import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductView {
    private int pageCount;
    private List<Product> productList;
}
