package com.example.Fstock.mapper;
import com.example.Fstock.dto.response.OrderItemDto;
import com.example.Fstock.entity.Order_Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")

public interface OrderItemMapper {
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    OrderItemDto toDto(Order_Item orderItem);

}
