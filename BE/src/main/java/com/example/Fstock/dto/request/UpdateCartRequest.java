package com.example.Fstock.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateCartRequest {
    private int cartId;
    private int productId;
    private String email;
    private int quantity;
}
