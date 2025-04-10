package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.response.CategoryDto;
import com.example.Fstock.exception.NotFoundException;
import com.example.Fstock.mapper.CategoryMapper;
import com.example.Fstock.entity.Category;
import com.example.Fstock.responsitory.CategoryRepository;
import com.example.Fstock.service.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<CategoryDto> getAllCategories() {
       List<Category> categories = categoryRepository.findAll();
       List<CategoryDto> categoryDtos = categoryMapper.toListCategoryDto(categories);
       return categoryDtos;
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("404 Not Found");
        }else {
            CategoryDto categoryDto = categoryMapper.toCategoryDto(category.get());
            return categoryDto;
        }

    }


}
