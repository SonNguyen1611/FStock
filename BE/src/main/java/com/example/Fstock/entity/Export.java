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
public class Export {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exportId;
    @Column( nullable = false)
    private int countProduct;
    @Column( nullable = false)
    private Date transactionDate;
    @Column(name = "import_price", nullable = false)
    private float exportPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "export")
    private List<Export_Detail> exportDetails;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}
