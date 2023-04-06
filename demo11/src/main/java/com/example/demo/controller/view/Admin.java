package com.example.demo.controller.view;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class Admin {
    @Autowired
    private UserService userService;
    @GetMapping("/view/admin")
    public String admin(Model model){
        String res = (SecurityContextHolder.getContext().getAuthentication().getName());
        if(res.equals("anonymousUser"))
            res = null;
        else{
            int userId = userService.findByUserName(res).getUserId();
            model.addAttribute("username" , res);
            model.addAttribute("userId" , userId);
        }
        return "admin";
    }
}
