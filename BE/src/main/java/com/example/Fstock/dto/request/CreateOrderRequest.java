package com.example.Fstock.dto.request;

import com.example.Fstock.dto.response.OrderItemDto;
import com.example.Fstock.enums.OrderStatus;
import com.example.Fstock.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    private String email;
    @NotBlank(message = "Order Id  must not be blank")
    private int orderId;
    @NotBlank(message = "Provice name must not be blank")
    private String provinceName;
    @NotBlank(message = "District name must not be blank")
    private String districtName;
    @NotBlank(message = "Ward name must not be blank")
    private String wardName;
    @NotBlank(message = "Specific Address must not be blank")
    @Size( max = 120, message = "Specific Address should be less than 120 characters")
    private String specificAddress;

    @NotBlank(message = "Recipient Name must not be blank")
    @Size( max = 120, message = "Recipient Name should be less than 120 characters")
    private String recipientName;
    @NotBlank(message = "Fist name must not be blank")
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private String note;
    @NotBlank(message = "Total price must not be blank")
    private BigDecimal totalPrice;
    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;
    private List<OrderItemDto> orderItems;

}
