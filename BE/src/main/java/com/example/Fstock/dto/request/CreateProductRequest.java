package com.example.Fstock.dto.request;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String productName;
    private String description;
    private BigDecimal priceDefault;
    private MultipartFile imageUrlDisplay;
    private List<MultipartFile> images;
    private String productSKU;
    private int categoryId;
    private List<ProductVariantRequest> variants;

}
