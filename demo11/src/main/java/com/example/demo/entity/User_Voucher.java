package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_voucher_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
}
