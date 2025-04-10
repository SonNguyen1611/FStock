package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.PermissionRequest;
import com.example.Fstock.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest permissionRequest);
    List<PermissionResponse> getAllPermission();
    void deletePermission(Long id);
}
