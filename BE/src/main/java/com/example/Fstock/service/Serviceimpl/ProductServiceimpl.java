package com.example.Fstock.service.Serviceimpl;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.dto.response.ReviewResponse;
import com.example.Fstock.entity.Review;
import com.example.Fstock.exception.NotFoundException;
import com.example.Fstock.mapper.ProductMapper;
import com.example.Fstock.entity.Product;
import com.example.Fstock.mapper.ReviewMapper;
import com.example.Fstock.responsitory.ProductRepository;
import com.example.Fstock.service.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ProductDto getProductById(int id) {
        Product product = productRepository.findByProductId(id);
        ProductDto productDto = productMapper.toDto(product);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        product.getReviews().forEach(review -> {
            reviewResponses.add(reviewMapper.toReviewResponse(review));
        } );
        double averageRating = product.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        productDto.setAverageRatings(Double.valueOf(decimalFormat.format(averageRating)));
        productDto.setReviewResponses(reviewResponses);

        return productDto;

    }

    @Override
    public Page<ProductDto> getAllProducts(int pageNumber ) {
        Pageable pageable = PageRequest.of(pageNumber-1, 9);
        Page<Product> pageProduct = productRepository.findAll(pageable);
        return pageProduct.map(productMapper::toDto) ;
    }

    @Override
    public Page<ProductDto> getProductByFilter(Integer priceMin,
                                               Integer priceMax,
                                               Integer colorId,
                                               Integer sizeId ,
                                               Integer categoryId,
                                               int pageNumber) {

        List<ProductDto> productDtos = productMapper.toListDto(productRepository.findAll());
        if(categoryId != null){
            List<Integer> productIdsByCategory = productRepository.findByCategoryCategoryId(categoryId)
                    .stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toList());


            //những phần tuwrnaof không nằm trong  productIdsByCategory sẽ bị loại
            productDtos.removeIf(productDto -> !productIdsByCategory.contains(productDto.getProductId()));
        }
        if(priceMin != null ){
            if(priceMax != null){
                productDtos.removeIf(productDto ->
                        productDto.getPriceDefault() == null ||
                                productDto.getPriceDefault().compareTo(BigDecimal.valueOf(priceMin)) < 0 ||
                                productDto.getPriceDefault().compareTo(BigDecimal.valueOf(priceMax)) > 0);
            }else{
                productDtos.removeIf(productDto ->
                        productDto.getPriceDefault() == null ||
                                productDto.getPriceDefault().compareTo(BigDecimal.valueOf(priceMin)) < 0) ;
            }
        }
        if (colorId != null){
            List<Integer> productIdsByColor = productRepository.findProductsByColor(colorId)
                    .stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toList());

            productDtos.removeIf(productDto -> !productIdsByColor.contains(productDto.getProductId()));
        }
        if (sizeId != null){
            List<Integer> productIdsBySize = productRepository.findProductsBySize(sizeId)
                    .stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toList());

            productDtos.removeIf(productDto -> !productIdsBySize.contains(productDto.getProductId()));
        }
        if(productDtos.isEmpty()) {
            throw new NotFoundException("List Product Not Found");
        }
        Pageable pageDetail = PageRequest.of(pageNumber-1, 9);
        int start = (int)pageDetail.getOffset();
        int end = (int)(pageDetail.getOffset()+pageDetail.getPageSize()> productDtos.size() ? productDtos.size() : pageDetail.getOffset()+pageDetail.getPageSize());
        List<ProductDto> productDtosFinal = productDtos.subList(start,end);
        return new PageImpl<>(productDtosFinal, pageDetail,productDtos.size());
    }

    @Override
    public Integer getQuantityInStock(int productId, String colorName, String sizeName) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        if ( colorName != null) {
            if(sizeName == null){
                Integer quantity = product.getProductVariants().stream()
                        .filter(pdVaviant -> pdVaviant.getColor().getColorName().equals(colorName))
                        .mapToInt(variant -> variant.getStockQuantity())
                        .sum();
                return quantity;
            }else {
                Integer quantity = product.getProductVariants().stream()
                        .filter(pdVaviant -> pdVaviant.getColor().getColorName().equals(colorName) && pdVaviant.getSize().getSizeName().equals(sizeName))
                        .mapToInt(variant -> variant.getStockQuantity())
                        .sum();
                return quantity;
            }
        }else{
            if(sizeName == null){
                Integer quantity = product.getProductVariants().stream()
                        .mapToInt(variant -> variant.getStockQuantity())
                        .sum();
                return quantity;
            }else {
                Integer quantity = product.getProductVariants().stream()
                        .filter(pdVaviant -> pdVaviant.getSize().getSizeName().equals(sizeName))
                        .mapToInt(variant -> variant.getStockQuantity())
                        .sum();
                return quantity;
            }
        }


    }


}
