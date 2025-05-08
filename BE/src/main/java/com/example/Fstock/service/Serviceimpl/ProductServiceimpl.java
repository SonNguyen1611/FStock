package com.example.Fstock.service.Serviceimpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Fstock.dto.request.CreateProductRequest;
import com.example.Fstock.dto.response.ColorInfo;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.dto.response.ReviewResponse;
import com.example.Fstock.entity.*;
import com.example.Fstock.exception.NotFoundException;
import com.example.Fstock.mapper.ProductMapper;
import com.example.Fstock.mapper.ProductVariantMapper;
import com.example.Fstock.mapper.ReviewMapper;
import com.example.Fstock.responsitory.CategoryRepository;
import com.example.Fstock.responsitory.ProductRepository;
import com.example.Fstock.responsitory.ProductVariantRepository;
import com.example.Fstock.service.Service.ProductService;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  Cloudinary cloudinary;

    @Override
    public ProductDto getProductById(int id) {
        Product product = productRepository.findByProductId(id);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
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
    public Page<ProductDto> getAllProducts(int pageNumber , int pageSize , String search) {
        List<ProductDto> productDtos =  productMapper.toListDto(productRepository.findAll());
        if (search != null && !search.isEmpty()) {
            List<ProductDto> productByKeyword = productMapper.toListDto( productRepository.findBySearchKeyword(search));
            if (productByKeyword.isEmpty()) {
                throw new NotFoundException("Product not found");
            }
            Set<Integer> productIdsByKeyword = productByKeyword.stream()
                    .map(ProductDto::getProductId)
                    .collect(Collectors.toSet());

            productDtos.removeIf(productDto -> !productIdsByKeyword.contains(productDto.getProductId()));
        }
        Pageable pageDetail = PageRequest.of(pageNumber-1, pageSize);
        int start = (int)pageDetail.getOffset();
        int end = (int)(pageDetail.getOffset()+pageDetail.getPageSize()> productDtos.size() ? productDtos.size() : pageDetail.getOffset()+pageDetail.getPageSize());
        List<ProductDto> productDtosFinal = productDtos.subList(start,end);
        return new PageImpl<>(productDtosFinal, pageDetail,productDtos.size());
    }

    @Override
    public Page<ProductDto> getProductByFilter(Integer priceMin,
                                               Integer priceMax,
                                               String sizeName,
                                               String colorName ,
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
        if (colorName != null){
            List<Integer> productIdsByColor = productRepository.findByProductVariantsColorName(colorName)
                    .stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toList());

            productDtos.removeIf(productDto -> !productIdsByColor.contains(productDto.getProductId()));
        }
        if (sizeName != null){
            List<Integer> productIdsBySize = productRepository.findByProductVariantsSizeName(sizeName)
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
                        .filter(pdVaviant -> pdVaviant.getColorName().equals(colorName))
                        .mapToInt(variant -> variant.getStockQuantity())
                        .sum();
                return quantity;
            }else {
                Integer quantity = product.getProductVariants().stream()
                        .filter(pdVaviant -> pdVaviant.getColorName().equals(colorName) && pdVaviant.getSizeName().equals(sizeName))
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
                        .filter(pdVaviant -> pdVaviant.getSizeName().equals(sizeName))
                        .mapToInt(variant -> variant.getStockQuantity())
                        .sum();
                return quantity;
            }
        }


    }

    @Override
    public List<ColorInfo> getAllColor() {
        return productVariantRepository.findDistinctColorNameAndColorCode();
    }

    @Override
    public List<String> getAllSize() {
        return productVariantRepository.findDistinctSizeName();
    }

    @Override
    @Transactional
    public void createProduct (CreateProductRequest createProductRequest) throws IOException {
        Product product = new Product();
        product.setProductName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getDescription());
        product.setPriceDefault(createProductRequest.getPriceDefault());
        if (productRepository.findByProductSKU(createProductRequest.getProductSKU()) != null) {
            throw new BadRequestException("SKU already exists");
        }
        product.setProductSKU(createProductRequest.getProductSKU());
        Category category = categoryRepository.findById(createProductRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        product.setCategory(category);
        List<ProductVariant> productVariants = new ArrayList<>();
        createProductRequest.getVariants().forEach(productVariantRequest -> {
            ProductVariant productVariant = productVariantMapper.toEntity(productVariantRequest);
            productVariant.setProduct(product);
            productVariants.add(productVariant);
        });
        product.setProductVariants(productVariants);
        product.setImageUrlDisplay(getImageUrlAfterUpload(createProductRequest.getImageUrlDisplay()));
        List<ProductImages> productImageList = new ArrayList<>();
        createProductRequest.getImages().forEach(multipartFile -> {
            try {
                ProductImages productImages = new ProductImages();
                String imageUrl = getImageUrlAfterUpload(multipartFile);
                productImages.setProductImagesUrl(imageUrl);
                productImages.setProduct(product);
                productImageList.add(productImages);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        product.setProductImages(productImageList);
        productRepository.save(product);
    }

    public String getImageUrlAfterUpload(MultipartFile multipartFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("folder", "fstock"));
        return (String) uploadResult.get("secure_url");
    }
    public String getPublicIdImageCloudinary(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String imageName = parts[parts.length - 1].split("\\.")[0];
        String folder = parts[parts.length - 2];
        String publicId = folder + "/" + imageName;
        return publicId;
    }

    @Override
    @Transactional
    public void deleteProduct(int productId) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        String imageUrlDisplay = product.getImageUrlDisplay();
        String publicIdImgDisplay = getPublicIdImageCloudinary(imageUrlDisplay);
        List<String> urlImagesGallery = product.getProductImages().stream()
                .map(ProductImages::getProductImagesUrl)
                .collect(Collectors.toList());
        List<String> publicIdImagesGallery = urlImagesGallery.stream()
                .map(url -> getPublicIdImageCloudinary(url))
                .collect(Collectors.toList());
        try{
            Map resultDisplay = cloudinary.uploader().destroy(publicIdImgDisplay, ObjectUtils.emptyMap());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        publicIdImagesGallery.forEach(publicId ->{
            try {
                Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void updateProduct(int productId, CreateProductRequest createProductRequest) throws IOException {
        Product product = productRepository.findByProductId(productId);
        if(product == null) {
            throw new NotFoundException("Product not found");
        }
        product.setProductName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getDescription());
        product.setPriceDefault(createProductRequest.getPriceDefault());
        product.setProductSKU(createProductRequest.getProductSKU());
        Category category = categoryRepository.findById(createProductRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        product.setCategory(category);
        List<ProductVariant> productVariants = product.getProductVariants();
        productVariants.clear();
        createProductRequest.getVariants().forEach(productVariantRequest -> {
            ProductVariant productVariant = productVariantMapper.toEntity(productVariantRequest);
            productVariant.setProduct(product);
            productVariants.add(productVariant);
        });
        if (createProductRequest.getImageUrlDisplay() != null) {
            String imageUrlDisplay = product.getImageUrlDisplay();
            String publicIdImgDisplay = getPublicIdImageCloudinary(imageUrlDisplay);
            try {
                Map resultDisplay = cloudinary.uploader().destroy(publicIdImgDisplay, ObjectUtils.emptyMap());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product.setImageUrlDisplay((getImageUrlAfterUpload(createProductRequest.getImageUrlDisplay())));

        }
        if (createProductRequest.getImages() != null) {
            //xóa list hình cũ
            List<ProductImages> productImageList = product.getProductImages();
            productImageList.forEach(productImages -> {
                String imageUrl = productImages.getProductImagesUrl();
                String publicIdImg = getPublicIdImageCloudinary(imageUrl);
                try {
                    Map resultDisplay = cloudinary.uploader().destroy(publicIdImg, ObjectUtils.emptyMap());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            //thêm hình mới
            List<ProductImages> newProductImageList = product.getProductImages();
            newProductImageList.clear();
            createProductRequest.getImages().forEach(multipartFile -> {
                try {
                    ProductImages productImages = new ProductImages();
                    String imageUrl = getImageUrlAfterUpload(multipartFile);
                    productImages.setProductImagesUrl(imageUrl);
                    productImages.setProduct(product);
                    newProductImageList.add(productImages);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        productRepository.save(product);
    }
}
