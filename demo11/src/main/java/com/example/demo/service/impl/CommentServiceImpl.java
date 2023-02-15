package com.example.demo.service.impl;

import com.example.demo.entity.Comment;
import com.example.demo.entity.dto.CommentDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommentService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment findById(int id) throws UserNotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No comment have id " + id));
    }

    @Override
    public List<Comment> findByProduct(int productId) {
        return commentRepository.findByProduct(productId);
    }

    @Override
    public List<Comment> findByUser(int userId) {
        return commentRepository.findByUser(userId);
    }

    @Override
    public Comment update(CommentDto commentDto, int id) throws UserNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No comment have id " + id));
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new UserNotFoundException("No user have id " + commentDto.getUserId())));
        comment.setProduct(productRepository.findById(commentDto.getProductId()).orElseThrow(() -> new UserNotFoundException("No product have id " + commentDto.getProductId())));
        comment.setContent(commentDto.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public Comment addComment(CommentDto commentDto) throws UserNotFoundException {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new UserNotFoundException("No user have id " + commentDto.getUserId())));
        comment.setProduct(productRepository.findById(commentDto.getProductId()).orElseThrow(() -> new UserNotFoundException("No product have id " + commentDto.getProductId())));
        return commentRepository.save(comment);
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No comment have id " + id));
        commentRepository.deleteById(id);
    }
}
