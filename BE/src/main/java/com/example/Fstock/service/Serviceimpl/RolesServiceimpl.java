package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.request.RolesRequest;
import com.example.Fstock.dto.response.RolesReponse;
import com.example.Fstock.entity.Permission;
import com.example.Fstock.entity.Roles;
import com.example.Fstock.mapper.RolesMapper;
import com.example.Fstock.responsitory.PermissionRepository;
import com.example.Fstock.responsitory.RolesRepository;
import com.example.Fstock.service.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolesServiceimpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RolesReponse createRole(RolesRequest rolesRequest) {
        Roles roles = new Roles();
        roles.setRoleName(rolesRequest.getRoleName());
        rolesRequest.getPermissions().forEach(permission -> {
            Set<Permission> permissions = new HashSet<>();
            permissions.add(permissionRepository.findByPermissionName(permission.getPermissionName()));
            roles.setPermissions(permissions);
        });
        rolesRepository.save(roles);
        return rolesMapper.toRolesReponse(roles);
    }

    @Override
    public List<RolesReponse> getAllRoles() {
        return rolesRepository.findAll().stream().map(rolesMapper::toRolesReponse).toList();
    }

    @Override
    public void deleteRole(long roleId) {
        rolesRepository.deleteById(roleId);
    }


}
