package com.example.Fstock.mapper;

import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<ProductDto> toListDto(List<Product> products);
    ProductDto toDto(Product product);

}
