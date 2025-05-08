package com.example.Fstock.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailResponse {
    private int cartId;
    private String email;
    private ProductDto product;
    private int quantity;
    private BigDecimal currentPrice;
    private String colorName;
    private String sizeName;

}
