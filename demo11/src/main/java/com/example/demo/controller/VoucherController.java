package com.example.demo.controller;

import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;
    @GetMapping("/voucher/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable int userId){
        return new ResponseEntity<>(voucherService.findByUserId(userId), HttpStatus.OK);
    }
}
