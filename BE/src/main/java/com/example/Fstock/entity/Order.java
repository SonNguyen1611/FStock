package com.example.Fstock.entity;

import com.example.Fstock.enums.OrderStatus;
import com.example.Fstock.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private int orderId;
    @Column( nullable = false)
    private LocalDateTime orderDate;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private String shippingAddress;
    
    @Column(nullable = true)
    private String note;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String recipientName;
    @Column(nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<Order_Item> orderItems;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private User user;

}
