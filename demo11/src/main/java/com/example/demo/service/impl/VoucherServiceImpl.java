package com.example.demo.service.impl;

import com.example.demo.entity.Voucher;
import com.example.demo.entity.dto.VoucherDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findById(int id) throws UserNotFoundException {
        return voucherRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No voucher have id " + id));
    }

    @Override
    public Voucher findByCode(String name) {
        return voucherRepository.findByCode(name);
    }

    @Override
    public Voucher updateVoucher(VoucherDto voucherDto, int id) throws UserNotFoundException, ObjectExistedException {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No voucher have id " + id));
        if(!ObjectUtils.isEmpty(voucherRepository.findByCode(voucherDto.getCode()))){
            throw new ObjectExistedException("Voucher existed!");
        }
        voucher.setCode(voucherDto.getCode());
        voucher.setPersen(voucherDto.getPersen());
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher createVoucher(VoucherDto voucherDto) throws ObjectExistedException {
        Voucher voucher = new Voucher();
        if(!ObjectUtils.isEmpty(voucherRepository.findByCode(voucherDto.getCode()))){
            throw new ObjectExistedException("Voucher existed!");
        }
        voucher.setPersen(voucherDto.getPersen());
        voucher.setCode(voucherDto.getCode());
        return voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(int id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public List<Voucher> findByUserId(int userId) {
        return voucherRepository.findByUserId(userId);
    }
}
