package com.example.demo.controller;

import com.example.demo.entity.dto.Cart;
import com.example.demo.service.SaleOrderService;
import com.example.demo.utils.utils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    private SaleOrderService saleOrderService;

    @PostMapping("/cart")
    public ResponseEntity<?> AddToCart(@RequestBody @Valid Cart cart , HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        int productId = cart.getProductId();
        if(cartMap == null)
            cartMap = new HashMap<>();
        if(cartMap.containsKey(productId)){
            Cart c = cartMap.get(productId);
            c.setQuantity(c.getQuantity() + 1);
        }
        else{
            cartMap.put(productId , cart);
        }
        session.setAttribute("cart" , cartMap);
        return new ResponseEntity<>(utils.aggregate(cartMap),HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<?> cart(HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        if(ObjectUtils.isEmpty(cartMap)){
            return new ResponseEntity<>("Gio Hang Trong" , HttpStatus.OK);
        }
        return new ResponseEntity<>(cartMap, HttpStatus.OK);
    }
    @PutMapping("/cart")
    public ResponseEntity<?> updateCart(@RequestBody @Valid  Cart cart , HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        Cart c = cartMap.get(cart.getProductId());
        c.setQuantity(cart.getQuantity());
        cartMap.put(cart.getProductId(), cart);
        session.setAttribute("cart" , cartMap);
        return new ResponseEntity<>("Cap Nhat Thanh Cong" , HttpStatus.OK);
    }
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable int id , HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        if(cartMap != null && cartMap.containsKey(id)){
            cartMap.remove(id);
            session.setAttribute("cart" , cartMap);
            return new ResponseEntity<>("Da Xoa Thanh Cong" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Da co loi xay ra" , HttpStatus.BAD_REQUEST);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cart/pay")
    public ResponseEntity<?> payment(HttpSession session , @RequestParam int id){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        if(cartMap != null){
            boolean check = saleOrderService.addOrder(cartMap , id);
            if(check == true){
                return new ResponseEntity<>("Pay Successful!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Something went wrong!" , HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("Nothing to pay" , HttpStatus.OK);
        }
    }
}
