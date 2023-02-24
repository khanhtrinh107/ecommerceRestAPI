package com.example.demo.service;

import com.example.demo.entity.SaleOrder;
import com.example.demo.entity.dto.Cart;

import java.util.List;
import java.util.Map;

public interface SaleOrderService {
    List<SaleOrder> findALl();
    SaleOrder findById(int id);
    SaleOrder create();

    boolean addOrder(Map<Integer, Cart> carts , int id , String code);
}
