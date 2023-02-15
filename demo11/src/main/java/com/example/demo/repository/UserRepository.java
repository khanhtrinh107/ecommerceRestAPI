package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Integer> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("select u from User u inner join SaleOrder s on u.userId = s.user.userId")
    List<User> findUserBuyProduct();

    @Query("select u.userId , u.username , sum(s.amount) from User u inner join SaleOrder s on u.userId = s.user.userId group by u.userId order by s.amount desc ")
    List<Object[]> findUserBuyTheMost();


}
