package com.example.demo.service.impl;

import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.SaleOrder;
import com.example.demo.entity.Voucher;
import com.example.demo.entity.dto.Cart;
import com.example.demo.repository.*;
import com.example.demo.service.SaleOrderService;
import com.example.demo.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    @Autowired
    private VoucherRepository voucherRepository;

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
    public boolean addOrder(Map<Integer, Cart> carts, int id , String code ) {
       try {
           System.out.println("OK");
           SaleOrder saleOrder = new SaleOrder();
           Voucher voucher = userRepository.findByUserIdAndCode(code,id);
           System.out.println("test");
           float giam = 1;
           if(!ObjectUtils.isEmpty(voucher)){
               giam = 1-(float)voucher.getPersen()/100;
           }
           saleOrder.setAmount(utils.aggregate(carts).get("amount1")*giam);
           System.out.println("OK0");
           saleOrder.setUser(userRepository.findById(id).orElseThrow());
           saleOrderRepository.save(saleOrder);
           System.out.println("OK1");
           for(Cart cart : carts.values()){
               OrderDetail orderDetail = new OrderDetail();
               orderDetail.setSaleOrder(saleOrder);
               orderDetail.setProduct(productRepository.findById(cart.getProductId()).orElseThrow());
               orderDetail.setInitPrice(cart.getPrice());
               orderDetail.setQuantity(cart.getQuantity());
               orderDetailRepository.save(orderDetail);
           }
           System.out.println("OK2");
           return true;
       }catch (Exception ex){
           System.out.println(ex.getMessage());
           ex.getStackTrace();
       }
        System.out.println("NOT OK");
        return false;
    }
}
