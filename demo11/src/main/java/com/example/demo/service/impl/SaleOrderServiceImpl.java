package com.example.demo.service.impl;

import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.SaleOrder;
import com.example.demo.entity.dto.Cart;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleOrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SaleOrderService;
import com.example.demo.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {
    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public List<SaleOrder> findALl() {
        return saleOrderRepository.findAll();
    }

    @Override
    public SaleOrder findById(int id) {
        return saleOrderRepository.findById(id).orElseThrow();
    }

    @Override
    public SaleOrder create() {
        return null;
    }

    @Override
    public boolean addOrder(Map<Integer, Cart> carts, int id) {
       try {
           SaleOrder saleOrder = new SaleOrder();
           saleOrder.setAmount(utils.aggregate(carts).get("amount"));
           saleOrder.setUser(userRepository.findById(id).orElseThrow());
           saleOrderRepository.save(saleOrder);
           for(Cart cart : carts.values()){
               OrderDetail orderDetail = new OrderDetail();
               orderDetail.setSaleOrder(saleOrder);
               orderDetail.setProduct(productRepository.findById(cart.getProductId()).orElseThrow());
               orderDetail.setInitPrice(cart.getPrice());
               orderDetail.setQuantity(cart.getQuantity());
               orderDetailRepository.save(orderDetail);
           }
           return  true;
       }catch (Exception ex){
           ex.getStackTrace();
       }
        return false;
    }
}
