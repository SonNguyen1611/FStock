package com.example.Fstock.mapper;
import com.example.Fstock.dto.response.OrderDto;
import com.example.Fstock.entity.Order;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring", uses = OrderItemMapper.class)

public interface OrderMapper {
    OrderDto toDto(Order order);
}
