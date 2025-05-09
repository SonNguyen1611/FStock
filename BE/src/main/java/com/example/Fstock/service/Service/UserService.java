package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.ChangeInfoRequest;
import com.example.Fstock.dto.request.ChangePasswordRequest;
import com.example.Fstock.dto.request.ChangeRolesRequest;
import com.example.Fstock.dto.request.CreationUser;
import com.example.Fstock.dto.response.UserResponse;
import com.example.Fstock.entity.User;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void createUser(CreationUser creationUser);
    UserResponse getUserById(int userId);
    UserResponse getMyInfo();
    Page<UserResponse> getAllUser(int pageNumber, int pageSize);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    void changeInfo(ChangeInfoRequest changeInfoRequest) throws IOException;
    void changeActiveStatus(String email, Boolean status);
    void changeRoles(ChangeRolesRequest changeRolesRequest);
    void deleteUser(String email);
}
