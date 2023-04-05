package com.example.demo.controller.view;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class Admin {
    @GetMapping("/view/admin")
    public String admin(){
        return "admin";
    }
}
