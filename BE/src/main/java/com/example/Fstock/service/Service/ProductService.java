package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.CreateProductRequest;
import com.example.Fstock.dto.response.ColorInfo;
import com.example.Fstock.dto.response.ProductDto;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ProductService  {
    ProductDto getProductById(int id);
    Page<ProductDto> getAllProducts(int pageNumber , int pageSize , String search);
    Page<ProductDto> getProductByFilter(Integer priceMin, Integer priceMax, String colorName, String sizeName, Integer categoryId, int pageNumber);
    Integer getQuantityInStock(int productId, String colorName, String sizeName);
    List<ColorInfo> getAllColor();
    List<String> getAllSize();
    void createProduct(CreateProductRequest createProductRequest) throws IOException;
    void deleteProduct(int productId);
    void updateProduct(int productId, CreateProductRequest createProductRequest) throws IOException;


}
