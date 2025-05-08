package com.example.Fstock.dto.response;

import com.example.Fstock.enums.OrderStatus;
import com.example.Fstock.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String orderId;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private String orderDate;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private String phoneNumber;
    private String recipientName;
    private String email;
    private String note;
    List<OrderItemDto> orderItems;

}
