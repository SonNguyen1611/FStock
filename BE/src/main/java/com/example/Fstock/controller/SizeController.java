package com.example.Fstock.controller;

import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.CategoryDto;
import com.example.Fstock.dto.response.SizeDto;
import com.example.Fstock.service.Service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {
    @Autowired
    private SizeService sizeService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllSize() {

        return  ResponseEntity.ok(ApiResponse.<List<SizeDto>>builder()
                .message("List size")
                .data(sizeService.getAllSizes())
                .build());
    }

}
