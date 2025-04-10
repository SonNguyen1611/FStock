package com.example.Fstock.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @Column( nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private long totalPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<Order_Item> orderItems;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private User user;

}
