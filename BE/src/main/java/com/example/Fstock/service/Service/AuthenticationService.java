package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.AuthenticationRequest;
import com.example.Fstock.dto.request.IntrospectRequest;
import com.example.Fstock.dto.request.LogoutRequest;
import com.example.Fstock.dto.request.RefreshTokenRequest;
import com.example.Fstock.dto.response.AuthenticationResponse;
import com.example.Fstock.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspectResponse(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException;
    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;
}

