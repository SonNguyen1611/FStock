package com.example.Fstock.controller;
import com.example.Fstock.dto.request.PermissionRequest;
import com.example.Fstock.dto.request.RolesRequest;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.PermissionResponse;
import com.example.Fstock.dto.response.RolesReponse;
import com.example.Fstock.service.Service.PermissionService;
import com.example.Fstock.service.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @PostMapping
    public ResponseEntity<ApiResponse> createPermission(@RequestBody RolesRequest rolesRequest) {
        rolesService.createRole(rolesRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Permission created successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRoles() {

        return ResponseEntity.ok(ApiResponse.<List<RolesReponse>>builder()
                .data(rolesService.getAllRoles())
                .build());
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Permission deleted successfully")
                .build());
    }


}
