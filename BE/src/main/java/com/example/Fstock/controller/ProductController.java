package com.example.Fstock.controller;

import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.service.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<ProductDto>> getAllProducts(@PathVariable int pageNumber){
        Page<ProductDto> productDtos = productService.getAllProducts(pageNumber);
        return new ResponseEntity<>(productDtos , HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") int productId){
        ProductDto productDto = productService.getProductById(productId);

        return  ResponseEntity.ok(ApiResponse.<ProductDto>builder()
                .message("Product detail")
                .data(productDto)
                .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> getProductsByFilter(
            @RequestParam(value = "priceMin", required = false) Integer priceMin,
            @RequestParam(value = "priceMax", required = false) Integer priceMax,
            @RequestParam(value = "color", required = false) Integer colorId,
            @RequestParam(value = "size", required = false) Integer sizeId,
            @RequestParam(value = "category", required = false) Integer categoryId,
            @RequestParam("pageNumber") Integer pageNumber
    )
    {
        Page<ProductDto> productDtos = productService.getProductByFilter(priceMin, priceMax, colorId, sizeId, categoryId, pageNumber);
        return  ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .message("Product list filtred")
                .data(productDtos)
                .build());
    }
    @GetMapping("/quantity")
    public ResponseEntity<ApiResponse> getQuantityInStock(
            @RequestParam(value = "productId") Integer productId,
            @RequestParam(value = "colorName", required = false) String colorName,
            @RequestParam(value = "sizeName", required = false) String sizeName

    )
    {
        return ResponseEntity.ok(ApiResponse.<Integer>builder()
                .message("Quantity in stock")
                .data(productService.getQuantityInStock(productId, colorName, sizeName))
                .build());

    }





}
