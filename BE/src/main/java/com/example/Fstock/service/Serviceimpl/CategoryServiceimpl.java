package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.request.CreateCategoryRequest;
import com.example.Fstock.dto.response.CategoryDto;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.exception.NotFoundException;
import com.example.Fstock.mapper.CategoryMapper;
import com.example.Fstock.entity.Category;
import com.example.Fstock.responsitory.CategoryRepository;
import com.example.Fstock.service.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


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
    //Lấy danh sách thể loại
    public Page<CategoryDto> getCategoriesPage (int pageNumber , int pageSize , String search) {
        List<CategoryDto> categoryDtos =  categoryMapper.toListCategoryDto(categoryRepository.findAll());
        if (search != null && !search.isEmpty()) {
            List<CategoryDto> categoriesByKeyword = categoryMapper.toListCategoryDto( categoryRepository.findByCategoryNameContaining(search));
            if (categoriesByKeyword.isEmpty()) {
                throw new NotFoundException("Category not found");
            }
            Set<Integer> categoryIdsByKeyword = categoriesByKeyword.stream()
                    .map(CategoryDto::getCategoryId)
                    .collect(Collectors.toSet());

            categoryDtos.removeIf(categoryDto -> !categoryIdsByKeyword.contains(categoryDto.getCategoryId()));
        }
        Pageable pageDetail = PageRequest.of(pageNumber-1, pageSize);
        int start = (int)pageDetail.getOffset();
        int end = (int)(pageDetail.getOffset()+pageDetail.getPageSize()> categoryDtos.size() ? categoryDtos.size() : pageDetail.getOffset()+pageDetail.getPageSize());
        List<CategoryDto> categoryDtosFinal = categoryDtos.subList(start,end);
        return new PageImpl<>(categoryDtosFinal, pageDetail,categoryDtos.size());
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

    @Override
    public CategoryDto createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = new Category();
        category.setCategoryName(createCategoryRequest.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(savedCategory);
    }
    @Override
    public void deleteCategory(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("404 Not Found");
        } else {
            categoryRepository.delete(category.get());
        }
    }
    @Override
    public void updateCategory(int id, CreateCategoryRequest createCategoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("404 Not Found");
        } else {
            Category category1 = category.get();
            category1.setCategoryName(createCategoryRequest.getCategoryName());
            categoryRepository.save(category1);
        }
    }

}
