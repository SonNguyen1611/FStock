package com.example.Fstock.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequest {
    private int productId;
    private String email;
    private int quantity;
    private String colorName;
    private String sizeName;
    private BigDecimal currentPrice;


}
