package com.example.Fstock.dto.request;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RolesRequest {
    private String roleName;
    private Set<PermissionRequest> permissions;
}
