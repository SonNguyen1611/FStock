package com.example.Fstock.dto.response;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDto {
    Integer productVariantId;
    Long price;
    Integer stockQuantity;
    SizeDto size;
    ColorDto color;
}