package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import com.example.demo.entity.dto.VoucherDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Voucher findByCode(String code);
}
