package com.example.Fstock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Import {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int importId;
    @Column( nullable = false)
    private int countProduct;
    @Column( nullable = false)
    private Date transactionDate;
    @Column( nullable = false)
    private float importPrice;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "importt")
    private List<Import_Detail> importDetails;





}
