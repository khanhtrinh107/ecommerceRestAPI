package com.example.demo.controller;

import com.example.demo.entity.dto.Cart;
import com.example.demo.entity.dto.CartMessage;
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
    public ResponseEntity<?> AddToCart(@RequestBody  Cart cart , HttpSession session){
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
            return new ResponseEntity<>(new CartMessage("Gio Hang Trong"), HttpStatus.OK);
        }
        return new ResponseEntity<>(cartMap, HttpStatus.OK);
    }
    @GetMapping("/cart/display")
    public ResponseEntity<?> display(HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        if(ObjectUtils.isEmpty(cartMap)){
            Map<String,Integer> ms = new HashMap<>();
            ms.put("count",0);
            ms.put("amount1" , 0);
            return new ResponseEntity<>(ms,HttpStatus.OK);
        }
        return new ResponseEntity<>(utils.aggregate(cartMap),HttpStatus.OK);
    }
    @PutMapping("/cart")
    public ResponseEntity<?> updateCart(@RequestBody   Cart cart , HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        Cart c = cartMap.get(cart.getProductId());
        c.setQuantity(cart.getQuantity());
        cartMap.put(cart.getProductId(), cart);
        session.setAttribute("cart" , cartMap);
        return new ResponseEntity<>(new CartMessage("Cap Nhat Thanh Cong") , HttpStatus.OK);
    }
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable int id , HttpSession session){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        if(cartMap != null && cartMap.containsKey(id)){
            cartMap.remove(id);
            session.setAttribute("cart" , cartMap);
            return new ResponseEntity<>(new CartMessage("Da Xoa Thanh Cong" ), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CartMessage("Da co loi xay ra") , HttpStatus.BAD_REQUEST);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cart/pay")
    public ResponseEntity<?> payment(HttpSession session , @RequestParam(name = "id") int id , @RequestParam(name = "voucher" , required = false , defaultValue = "") String voucher){
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cart");
        if(cartMap != null){
            boolean check = saleOrderService.addOrder(cartMap , id , voucher);
            if(check == true){
                session.removeAttribute("cart");
                return new ResponseEntity<>(new CartMessage("Pay Successful!"), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new CartMessage("Something went wrong!" ), HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(new CartMessage("Nothing to pay") , HttpStatus.OK);
        }
    }
}
