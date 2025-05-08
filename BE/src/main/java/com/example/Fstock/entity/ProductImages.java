package com.example.Fstock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_images")
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer productImagesId;
    @Column
    private String productImagesName;
    @Column(nullable = false)
    private String productImagesUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
