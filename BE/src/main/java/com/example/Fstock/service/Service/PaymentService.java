package com.example.Fstock.service.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface PaymentService {
    String createOrderVNPAY(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;
}
