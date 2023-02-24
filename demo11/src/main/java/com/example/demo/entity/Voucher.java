package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "voucher")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucherId")
    private Integer voucherId;
    private String code;
    private int persen;
    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL , mappedBy = "voucher")
    private Set<SaleOrder> saleOrders;
    @OneToMany(fetch = FetchType.EAGER , cascade =  CascadeType.ALL , mappedBy = "voucher")
    private Set<User_Voucher> user_vouchers;
}
