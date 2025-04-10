package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.RolesRequest;
import com.example.Fstock.dto.response.RolesReponse;

import java.util.List;

public interface RolesService {
    RolesReponse createRole(RolesRequest rolesRequest);
    List<RolesReponse> getAllRoles();
    void deleteRole(long roleId);

}
