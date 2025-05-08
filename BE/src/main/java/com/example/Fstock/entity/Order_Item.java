package com.example.Fstock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailId;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column( nullable = false)
    private BigDecimal priceAtOrder;
    @Column( nullable = false)
    private String imageUrlDisplay;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
