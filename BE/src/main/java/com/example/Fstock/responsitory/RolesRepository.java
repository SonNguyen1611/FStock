package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(String roleName);
}
