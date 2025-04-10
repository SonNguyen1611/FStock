package com.example.Fstock.mapper;

import com.example.Fstock.dto.response.ColorDto;
import com.example.Fstock.entity.Color;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    List<ColorDto> toColorDtoList(List<Color> colors);
    ColorDto toColorDto(Color color);
}
