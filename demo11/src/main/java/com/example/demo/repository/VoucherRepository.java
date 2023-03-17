package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import com.example.demo.entity.dto.VoucherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Voucher findByCode(String code);
    @Query("select v from Voucher v inner join User_Voucher uv on v.voucherId = uv.voucher.voucherId where uv.user.userId = ?1")
    List<Voucher> findByUserId(int userId);
}
