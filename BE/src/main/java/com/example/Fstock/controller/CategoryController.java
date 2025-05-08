package com.example.Fstock.controller;

import com.example.Fstock.dto.request.CreateCategoryRequest;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.CategoryDto;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.service.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable ("id") int categoryId) {
        return  ResponseEntity.ok(ApiResponse.<CategoryDto>builder()
                .message("Danh sách thể loại")
                .data(categoryService.getCategoryById(categoryId))
                .build());
    }
    @GetMapping("/page")
    public ResponseEntity<ApiResponse> getCategoriesPage(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "search", defaultValue = "") String search) {
        return  ResponseEntity.ok(ApiResponse.<Page<CategoryDto>>builder()
                .message("Category page")
                .data(categoryService.getCategoriesPage(pageNumber, pageSize, search))
                .build());
    }
    @PostMapping("/create-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        return  ResponseEntity.ok(ApiResponse.<CategoryDto>builder()
                .message("Create category successfully")
                .data(categoryService.createCategory(createCategoryRequest))
                .build());
    }
    @DeleteMapping ("/delete/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return  ResponseEntity.ok(ApiResponse.builder()
                .message("Delete category successfully")
                .build());
    }

    @PutMapping ("/update/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId,
                                                        @RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.updateCategory(categoryId, createCategoryRequest);
        return  ResponseEntity.ok(ApiResponse.builder()
                .message("Update category successfully")
                .build());
    }
}
