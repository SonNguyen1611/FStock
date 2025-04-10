package com.example.Fstock.service.Service;

import com.example.Fstock.dto.response.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService  {
    ProductDto getProductById(int id);
    Page<ProductDto> getAllProducts(int pageNumber);
    Page<ProductDto> getProductByFilter(Integer priceMin, Integer priceMax, Integer colorId, Integer sizeId, Integer categoryId, int pageNumber);
    Integer getQuantityInStock(int productId, String colorName, String sizeName);


}
