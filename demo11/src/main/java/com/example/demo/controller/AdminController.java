package com.example.demo.controller;

import com.example.demo.entity.dto.UserBuyTheMost;
import com.example.demo.entity.dto.VoucherDto;
import com.example.demo.entity.dto.statistics;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private VoucherService voucherService;
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


    @GetMapping("/voucher")
    public ResponseEntity<?> getVouchers(){
        return new ResponseEntity<>(voucherService.findAll(),HttpStatus.OK);
    }


    @PostMapping("/voucher")
    public ResponseEntity<?> addVoucher(@RequestBody VoucherDto voucherDto) throws ObjectExistedException {
        return new ResponseEntity<>(voucherService.createVoucher(voucherDto),HttpStatus.CREATED);
    }

    @PutMapping("/voucher/{id}")
    public ResponseEntity<?> updateVoucher(@RequestBody VoucherDto voucherDto , @PathVariable int id) throws UserNotFoundException, ObjectExistedException {
        return new ResponseEntity<>(voucherService.updateVoucher(voucherDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/voucher/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable int id){
        voucherService.deleteVoucher(id);
        return new ResponseEntity<>("Delete Successful!",HttpStatus.OK);
    }

}
