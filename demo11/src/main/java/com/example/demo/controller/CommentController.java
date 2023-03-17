package com.example.demo.controller;

import com.example.demo.entity.dto.CommentDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
//@PreAuthorize("isAuthenticated()")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping
    public ResponseEntity<?> getCommentForProduct(@RequestParam(name = "productId") int productId ){
        return new ResponseEntity<>(commentService.findByProduct(productId), HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentDto commentDto) throws UserNotFoundException {
        return new ResponseEntity<>(commentService.addComment(commentDto),HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody @Valid CommentDto commentDto , @RequestParam(name = "commentId") int id) throws UserNotFoundException {
        return new ResponseEntity<>(commentService.update(commentDto , id) , HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam(name = "commentId") int id) throws UserNotFoundException {
        commentService.delete(id);
        return new ResponseEntity<>("Delete Successful!",HttpStatus.OK);
    }
}
