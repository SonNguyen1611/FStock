package com.example.Fstock.mapper;
import com.example.Fstock.dto.response.CategoryDto;
import com.example.Fstock.entity.Category;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
    List<CategoryDto> toListCategoryDto(List<Category> category);

}
