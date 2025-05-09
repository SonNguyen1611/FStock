package com.example.Fstock.responsitory;

import com.example.Fstock.entity.User_Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_RoleRepository extends JpaRepository<User_Roles, Integer> {
    boolean existsByUser_UserIdAndRoles_RoleId(Integer userId, Long roleId);
}
