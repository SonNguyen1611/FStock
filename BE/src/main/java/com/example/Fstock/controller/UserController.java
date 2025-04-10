package com.example.Fstock.controller;

import com.example.Fstock.dto.request.CreationUser;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.UserResponse;
import com.example.Fstock.responsitory.UserRepository;
import com.example.Fstock.service.Service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CreationUser user) {
        userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User Created")
                .build());


    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable int id) {
        UserResponse userResponse = userService.getUserById(id);
        return  ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .message("User by id")
                .data(userResponse)
                .build());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        return  ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .message("My info")
                .data(userResponse)
                .build());
    }
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUsers() {

        return  ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .message("User by id")
                .data(userService.getAllUser())
                .build());
    }





}
