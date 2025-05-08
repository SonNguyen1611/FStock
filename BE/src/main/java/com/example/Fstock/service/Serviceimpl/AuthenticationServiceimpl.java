package com.example.Fstock.service.Serviceimpl;

import com.example.Fstock.dto.request.AuthenticationRequest;
import com.example.Fstock.dto.request.IntrospectRequest;
import com.example.Fstock.dto.request.LogoutRequest;
import com.example.Fstock.dto.request.RefreshTokenRequest;
import com.example.Fstock.dto.response.AuthenticationResponse;
import com.example.Fstock.dto.response.IntrospectResponse;
import com.example.Fstock.entity.InvalidatedToken;
import com.example.Fstock.entity.User;
import com.example.Fstock.exception.NotFoundException;
import com.example.Fstock.exception.UnAuthorizedException;
import com.example.Fstock.responsitory.InvalidatedTokenRepository;
import com.example.Fstock.responsitory.UserRepository;
import com.example.Fstock.service.Service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationServiceimpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.secretKey}")
    protected String secretKey;

    @Value("${jwt.access-duration}")
    protected String accessTokenDuration; // thời gian tồn tại của token

    @Value("${jwt.refresh-duration}")
    protected String refreshTokenDuration; // thời gian refresh token



    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
       User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new UnAuthorizedException("Password and Email incorrect");
        }

        String token = generateJwtToken(user);

        return  AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();


    }
    @Override
    public void logout(LogoutRequest logoutRequest)  throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(logoutRequest.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);

        } catch (UnAuthorizedException exception) {
            log.info("Token already expired");
        }

    }
    @Override
    public IntrospectResponse introspectResponse(IntrospectRequest  introspectRequest) throws JOSEException, ParseException {
        String token = introspectRequest.getToken();

        boolean isvalid = true;

        try {
            verifyToken(token , false);
        } catch (UnAuthorizedException e) {
            isvalid = false;
        }
        return IntrospectResponse
                .builder()
                .valid(isvalid)
                .build();
    }

    private String generateJwtToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("furniturestock.com")
                .issueTime(new Date())// thời gian phát hành token
                .expirationTime(new Date(
                        Instant.now().plus(Long.parseLong(accessTokenDuration) , ChronoUnit.SECONDS).toEpochMilli()
                ))// thời hạn tồn tại của token
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        log.info(accessTokenDuration);

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));// kí token
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("cannot create token");
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        var signedJWT = verifyToken(refreshTokenRequest.getToken() , true);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();// get id token
        var expriryTime = signedJWT.getJWTClaimsSet().getExpirationTime();// get thời gian hết hạn token

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expriryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var email = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        String token = generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    // verify token
    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(Long.parseLong(refreshTokenDuration), ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new UnAuthorizedException("Token này không hợp lệ hoặc đã hết hạn");
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new UnAuthorizedException("Token này đã bị vô hiệu hóa");
        }
        return signedJWT;
    }


    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!user.getUserRoles().isEmpty()){
            user.getUserRoles().forEach(user_roles -> {
                stringJoiner.add("ROLE_" + user_roles.getRoles().getRoleName());
                user_roles.getRoles().getPermissions().forEach(permission -> {
                    stringJoiner.add(permission.getPermissionName());
                });
            });
        }
        return stringJoiner.toString();
    }


}
