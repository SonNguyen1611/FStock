package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.CreateCategoryRequest;
import com.example.Fstock.dto.response.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(int id);
    CategoryDto createCategory(CreateCategoryRequest createCategoryRequest);
    Page<CategoryDto> getCategoriesPage(int pageNumber, int pageSize, String search);
    void deleteCategory(int categoryId);
    void updateCategory(int categoryId, CreateCategoryRequest createCategoryRequest);

}
