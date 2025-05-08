package com.example.Fstock.dto.response;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private int quantity;
    private BigDecimal priceAtOrder;
    private int productId;
    private String productName;
    private String imageUrlDisplay;

}
