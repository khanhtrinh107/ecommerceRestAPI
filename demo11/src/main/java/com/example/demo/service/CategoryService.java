package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.dto.CategoryDto;
import com.example.demo.exception.ObjectExistedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<Category> findAll(int page , int size);

    Category findById(int id);

    List<Category> getAll();
    Category create(CategoryDto categoryDto) throws ObjectExistedException;
    Category update(CategoryDto categoryDto, int id) throws ObjectExistedException;
    void delete(int id);
}
