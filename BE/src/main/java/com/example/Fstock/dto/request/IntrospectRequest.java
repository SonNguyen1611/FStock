package com.example.Fstock.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// verify token
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IntrospectRequest {
    private String token;
}
