package com.example.demo.controller;

import com.example.demo.entity.dto.UserBuyTheMost;
import com.example.demo.entity.dto.statistics;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/buy")
    public ResponseEntity<?> findUserBuyProduct(){
        return new ResponseEntity<>(userService.findUserBuyProduct(), HttpStatus.OK);
    }
    @GetMapping("/statistics")
    public ResponseEntity<?> statistics(){
        List<Object[]> items = productRepository.statitics();
        List<statistics> list  = new ArrayList<>();
        for(Object[] o : items){
            list.add(new statistics(o[0].toString() , Integer.parseInt(o[1].toString()),Double.parseDouble(o[2].toString())));
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/userBuyTheMost")
    public ResponseEntity<?> userBuyTheMost(){
        List<Object[]> list = userService.findUserBuyTheMost();
        List<UserBuyTheMost> userBuyTheMosts = new ArrayList<>();
        for(Object[] o : list){
            userBuyTheMosts.add(new UserBuyTheMost(Integer.parseInt(o[0].toString()),o[1].toString(),Double.parseDouble(o[2].toString())));
        }
        return new ResponseEntity<>(userBuyTheMosts,HttpStatus.OK);
    }
}
