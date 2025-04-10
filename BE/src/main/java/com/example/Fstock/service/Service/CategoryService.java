package com.example.Fstock.service.Service;

import com.example.Fstock.dto.response.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    void save(CategoryDto categoryDto);
    CategoryDto getCategoryById(int id);


}
