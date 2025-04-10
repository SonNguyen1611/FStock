package com.example.Fstock.controller;
import com.example.Fstock.dto.request.PermissionRequest;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.PermissionResponse;
import com.example.Fstock.service.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")

public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        permissionService.createPermission(permissionRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Permission created successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPermission() {

        return ResponseEntity.ok(ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAllPermission())
                .build());
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<ApiResponse> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("Permission deleted successfully")
                .build());
    }
}
