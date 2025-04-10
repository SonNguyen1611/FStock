package com.example.Fstock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Import_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int importDetailId;

    private int inLast;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "import_id")
    private Import importt;


}
