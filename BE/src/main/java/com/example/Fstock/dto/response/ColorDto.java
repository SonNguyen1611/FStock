package com.example.Fstock.dto.response;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto  {
    Integer id;
    String colorName;
    String colorCode;
}