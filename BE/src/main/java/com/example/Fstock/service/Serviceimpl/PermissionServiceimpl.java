package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.request.PermissionRequest;
import com.example.Fstock.dto.response.PermissionResponse;
import com.example.Fstock.entity.Permission;
import com.example.Fstock.mapper.PermissionMapper;
import com.example.Fstock.responsitory.PermissionRepository;
import com.example.Fstock.service.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceimpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.toPermissionResponseList(permissions);
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
