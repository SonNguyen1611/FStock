package com.example.Fstock.dto.request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeRolesRequest {
    private String email;
    private List<String> roleNames;
}
