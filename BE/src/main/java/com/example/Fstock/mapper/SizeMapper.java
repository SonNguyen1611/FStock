package com.example.Fstock.mapper;

import com.example.Fstock.dto.response.SizeDto;
import com.example.Fstock.entity.Size;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    SizeDto toSizeDto(Size size);
    List<SizeDto> toSizeDtoList(List<Size> size);
}
