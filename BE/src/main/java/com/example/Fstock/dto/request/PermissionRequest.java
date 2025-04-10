package com.example.Fstock.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PermissionRequest {
    private String permissionName;
    private String permissionDescription;
}
