package com.example.demo.utils;

import com.example.demo.entity.dto.Cart;

import java.util.HashMap;
import java.util.Map;

public class utils {
    public static int count(Map<Integer , Cart> ms){
        int c = 0;
        for(Cart cart : ms.values()){
            c += cart.getQuantity();
        }
        return c;
    }
    public static Map<String,Double> aggregate(Map<Integer,Cart> ms){
        Map<String,Double> res = new HashMap<>();
        double sum = 0, cnt = 0;
        for(Cart cart : ms.values()){
            sum += cart.getPrice() * cart.getQuantity();
            cnt += cart.getQuantity();
        }
       res.put("count" , cnt);
        res.put("amount1" , Double.parseDouble(String.format("%.2f" , sum)));
        return res;
    }
}
