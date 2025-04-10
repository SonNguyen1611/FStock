package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.CreationUser;
import com.example.Fstock.dto.response.UserResponse;
import com.example.Fstock.entity.User;

import java.util.List;

public interface UserService {
    void createUser(CreationUser creationUser);
    UserResponse getUserById(int userId);
    UserResponse getMyInfo();
    List<UserResponse> getAllUser();
}
