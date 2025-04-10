package com.example.Fstock.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class SizeDto {
    Integer sizeId;
    String sizeName;
    String dimention;
    float weight;
}