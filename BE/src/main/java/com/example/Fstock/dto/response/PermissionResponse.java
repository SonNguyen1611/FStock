package com.example.Fstock.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PermissionResponse {
    private String permissionName;
    private String permissionDescription;

}
