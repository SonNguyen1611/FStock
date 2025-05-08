package com.example.Fstock.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDto {
    private Integer productVariantId;
    private Long price;
    private Integer stockQuantity;
    private String sizeName;
    private float length;
    private float width;
    private float height;
    private double weight;
    private String colorName;
    private String colorCode;
}