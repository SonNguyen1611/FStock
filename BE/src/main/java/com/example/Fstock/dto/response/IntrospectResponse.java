package com.example.Fstock.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
// response cho việc verify token
public class IntrospectResponse {
    boolean valid;
}
