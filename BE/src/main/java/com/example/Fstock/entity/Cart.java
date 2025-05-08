package com.example.Fstock.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column
    private BigDecimal currentPrice;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="product_variant_id")
    private ProductVariant productVariant;
}
