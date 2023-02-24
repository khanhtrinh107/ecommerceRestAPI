package com.example.demo.service;

import com.example.demo.entity.Voucher;
import com.example.demo.entity.dto.VoucherDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;

import java.util.List;

public interface VoucherService {
    public List<Voucher> findAll();
    public Voucher findById(int id) throws UserNotFoundException;
    public Voucher findByCode(String name);
    public Voucher updateVoucher(VoucherDto voucherDto , int id) throws UserNotFoundException, ObjectExistedException;
    public Voucher createVoucher(VoucherDto voucherDto) throws ObjectExistedException;

    public void deleteVoucher(int id);
}
