package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.ChangeInfoRequest;
import com.example.Fstock.dto.request.ChangePasswordRequest;
import com.example.Fstock.dto.request.CreationUser;
import com.example.Fstock.dto.response.UserResponse;
import com.example.Fstock.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    void createUser(CreationUser creationUser);
    UserResponse getUserById(int userId);
    UserResponse getMyInfo();
    Page<UserResponse> getAllUser(int pageNumber, int pageSize);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    void changeInfo(ChangeInfoRequest changeInfoRequest);
}
