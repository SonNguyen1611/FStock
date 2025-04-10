package com.example.Fstock.dto.response;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RolesReponse {
    private long roleId;
    private String roleName;
    private Set<PermissionResponse> permissions;
}
