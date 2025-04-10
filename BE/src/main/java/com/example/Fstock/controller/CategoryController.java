package com.example.Fstock.controller;

import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.CategoryDto;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.service.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();

        return  ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .message("Danh sách thể loại")
                .data(categoryDtos)
                .build());
    }
}
