package com.example.demo.controller.view;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.ProductView;
import com.example.demo.security.UserDetail;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @GetMapping("/view/home")
    public String view(){
        return "index";
    }
    @GetMapping("view/deltail/{id}")
    public String detail(@PathVariable int id , Model model){
        String res = (SecurityContextHolder.getContext().getAuthentication().getName());
        if(res.equals("anonymousUser"))
            res = null;
        else{
            int userId = userService.findByUserName(res).getUserId();
            model.addAttribute("username" , res);
            model.addAttribute("userId" , userId);
            model.addAttribute("productId" , id);
        }
        return "shop-details";
    }
    @GetMapping("view/cart")
    public String cart(Model model){
        String res = (SecurityContextHolder.getContext().getAuthentication().getName());
        if(res.equals("anonymousUser"))
            res = null;
        else{
            int userId = userService.findByUserName(res).getUserId();
            model.addAttribute("username" , res);
            model.addAttribute("userId" , userId);
        }
        return "shoping-cart";
    }
    @GetMapping("/view/shop")
    public String shop(Model model){
        String res = (SecurityContextHolder.getContext().getAuthentication().getName());
        if(res.equals("anonymousUser"))
            res = null;
        else{
            int userId = userService.findByUserName(res).getUserId();
            model.addAttribute("username" , res);
            model.addAttribute("userId" , userId);
        }
        return "shop-grid";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/view/search")
    public String handleSearch(Model model , @RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" ,required = false , defaultValue = "6") int size,@RequestParam(name = "domain" , required = false , defaultValue = "productId") String domain , @RequestParam(name = "dir" ,required = false , defaultValue = "asc") String dir , @RequestParam(name = "keyword" ,required = false , defaultValue = "") String keyword ){
        List<Product> list = productService.searchByProductName(size,page,domain,dir,keyword).getContent();
        System.out.println(list);
        int pageCount = productService.searchByProductName(size,page,domain,dir,keyword).getTotalPages();
        String res = (SecurityContextHolder.getContext().getAuthentication().getName());
        if(res.equals("anonymousUser"))
            res = null;
        else{
            int userId = userService.findByUserName(res).getUserId();
            model.addAttribute("username" , res);
            model.addAttribute("userId" , userId);
        }
        model.addAttribute("keyword" , keyword);
        model.addAttribute("list" , list);
        model.addAttribute("pageCount" , pageCount);
        return "search";
    }



}
