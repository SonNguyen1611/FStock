package com.example.Fstock.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.Fstock.entity.Category}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    Integer categoryId;
    String categoryName;
}