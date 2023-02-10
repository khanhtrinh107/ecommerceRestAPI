package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.dto.SignUpRequest;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;

public interface UserService {
    User findByUserName(String username);
    User findByEmail(String email);
    User create(SignUpRequest sign) throws ObjectExistedException;
    User update(SignUpRequest sign , int id) throws UserNotFoundException, ObjectExistedException;

    User findById(int id) throws UserNotFoundException;
    void delete(int id) throws UserNotFoundException;
}
