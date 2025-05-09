package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(String roleName);
}
