package com.example.Fstock.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Export_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exportDetailId;

    private int outLast;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "export_id")
    private Export export;


}
