package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category create(CategoryDto categoryDto);
    Category update(CategoryDto categoryDto, int id);
    void delete(int id);
}
