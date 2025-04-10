package com.example.Fstock.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
