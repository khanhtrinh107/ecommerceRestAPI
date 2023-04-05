package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.dto.*;
import com.example.demo.exception.ObjectExistedException;
//import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.security.UserDetail;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(){
        return "public page";
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername() , loginRequest.getPassword()));
//        UserDetail user = (UserDetail) authentication.getPrincipal();
//        String token = jwtTokenProvider.generateToken(user);
//        if(ObjectUtils.isEmpty(user)){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        else{
//            return new ResponseEntity<>(new ResponseUser(token, user.getUsername(), (List<GrantedAuthority>) user.getAuthorities()),HttpStatus.OK);
//        }
//    }
    @PostMapping("/home")
    public ResponseEntity<?> create(@RequestBody @Valid SignUpRequest sign) throws ObjectExistedException {
        return new ResponseEntity<>(userService.create(sign), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" , required = false , defaultValue = "6") int size){
        List<User> users = userService.findAll(page,size).getContent();
        int pageCount = userService.findAll(page,size).getTotalPages();
        return new ResponseEntity<>(new UserView(pageCount,users),HttpStatus.OK);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) throws UserNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(new CartMessage("delete successfully") , HttpStatus.OK);
    }
    @GetMapping("/auth")
    public String authen(){
        return "Authenticated page";
    }
    @GetMapping("/admin")
    public String admin(){
        return "Admin page";
    }
    @GetMapping("/editor")
    public String editor(){
        return "Editor page";
    }

}
