package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByPermissionName(String permissionName);

}
