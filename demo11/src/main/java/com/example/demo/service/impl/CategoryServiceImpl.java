package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.dto.CategoryDto;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Page<Category> findAll(int page , int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(CategoryDto categoryDto) throws ObjectExistedException {
        Category category = new Category();
        if(!ObjectUtils.isEmpty(categoryRepository.findByCategoryName(categoryDto.getCategoryName()))){
            throw new ObjectExistedException("category existed!");
        }
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category update(CategoryDto categoryDto, int id) throws ObjectExistedException {
        Category category = categoryRepository.findById(id).orElseThrow();
        if(!ObjectUtils.isEmpty(categoryRepository.findByCategoryName(categoryDto.getCategoryName()))){
            throw new ObjectExistedException("category existed!");
        }
        category.setDescription(categoryDto.getDescription());
        category.setCategoryName(categoryDto.getCategoryName());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}
