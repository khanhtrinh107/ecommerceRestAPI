package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Integer> {
    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("select u from User u inner join SaleOrder s on u.userId = s.user.userId")
    List<User> findUserBuyProduct();

    @Query("select u.userId , u.username , sum(s.amount) from User u inner join SaleOrder s on u.userId = s.user.userId group by u.userId order by s.amount desc ")
    List<Object[]> findUserBuyTheMost();
    @Query("select v from Voucher v inner join User_Voucher uv on v.voucherId = uv.voucher.voucherId where v.code = ?1 and uv.user.userId = ?2")
    Voucher findByUserIdAndCode(String code , int id);
}
