package com.example.Fstock.mapper;

import com.example.Fstock.dto.request.PermissionRequest;
import com.example.Fstock.dto.response.PermissionResponse;
import com.example.Fstock.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);
    Permission toPermission(PermissionRequest permissionRequest);
    List<PermissionResponse> toPermissionResponseList(List<Permission> permissions);
}
