package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.dto.CommentDto;
import com.example.demo.exception.UserNotFoundException;

import java.util.List;

public interface CommentService {
    Comment findById(int id) throws UserNotFoundException;
    List<Comment> findByProduct(int productId);
    List<Comment> findByUser(int userId);

    Comment update(CommentDto commentDto , int id) throws UserNotFoundException;

    Comment addComment(CommentDto commentDto) throws UserNotFoundException;

    void delete(int id) throws UserNotFoundException;
}
