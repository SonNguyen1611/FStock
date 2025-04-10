package com.example.Fstock.mapper;
import com.example.Fstock.dto.response.PermissionResponse;
import com.example.Fstock.dto.response.RolesReponse;
import com.example.Fstock.dto.response.UserResponse;
import com.example.Fstock.entity.User;
import com.example.Fstock.entity.User_Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

     @Mapping(target = "roles", source = "userRoles", qualifiedByName = "mapUserRolesToRoles")
     UserResponse toUserResponse(User user);

     @Named("mapUserRolesToRoles")
     default List<RolesReponse> mapUserRolesToRoles(List<User_Roles> userRoles) {
          return userRoles.stream().map(userRole -> RolesReponse.builder()
                  .roleId(userRole.getRoles().getRoleId())
                  .roleName(userRole.getRoles().getRoleName())
                  .permissions(userRole.getRoles().getPermissions()
                          .stream().map(permission -> PermissionResponse.builder()
                                  .permissionName(permission.getPermissionName())
                                  .permissionDescription(permission.getPermissionDescription())
                                  .build()).collect(Collectors.toSet()))
                  .build()).toList();
     }

}
