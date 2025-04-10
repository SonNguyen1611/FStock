package com.example.Fstock.dto.response;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImagesDto {
    Integer productImagesId;
    String productImagesName;
    String productImagesUrl;
}