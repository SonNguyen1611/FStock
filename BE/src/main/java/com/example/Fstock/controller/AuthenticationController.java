package com.example.Fstock.controller;

import com.example.Fstock.dto.request.AuthenticationRequest;
import com.example.Fstock.dto.request.IntrospectRequest;
import com.example.Fstock.dto.request.LogoutRequest;
import com.example.Fstock.dto.request.RefreshTokenRequest;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.AuthenticationResponse;
import com.example.Fstock.dto.response.IntrospectResponse;
import com.example.Fstock.service.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private  AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> login (
            @RequestBody AuthenticationRequest authenticationRequest)
    {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        return ResponseEntity.ok
                (ApiResponse.<AuthenticationResponse>builder()
                .message("success login")
                .data(authenticationResponse)
                .build()
        );

    }
    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse> introspect (
            @RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {

        return ResponseEntity.ok
                (ApiResponse.<IntrospectResponse>builder()
                            .message("Token is valid")
                        .data(authenticationService.introspectResponse(introspectRequest))
                        .build()
                );

    }
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken (
            @RequestBody RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshTokenRequest );

        return ResponseEntity.ok
                (ApiResponse.<AuthenticationResponse>builder()
                        .message("Token is valid")
                        .data(result)
                        .build()
                );

    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout (
            @RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest );
        return ResponseEntity.ok
                (ApiResponse.<AuthenticationResponse>builder()
                        .message("Logout success")
                        .build()
                );

    }


}
