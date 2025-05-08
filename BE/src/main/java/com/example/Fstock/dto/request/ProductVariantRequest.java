package com.example.Fstock.dto.request;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {
    private Integer stockQuantity;
    private String sizeName;
    private float length;
    private float width;
    private float height;
    private double weight;
    private String colorName;
    private String colorCode;
}
