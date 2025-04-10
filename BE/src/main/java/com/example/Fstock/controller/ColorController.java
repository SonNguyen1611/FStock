package com.example.Fstock.controller;

import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.ColorDto;
import com.example.Fstock.dto.response.ProductDto;
import com.example.Fstock.service.Service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/colors")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllColors () {
        List<ColorDto> colorDtos = colorService.getAllColors();

        return  ResponseEntity.ok(ApiResponse.<List<ColorDto>>builder()
                .message("Danh sách màu")
                .data(colorDtos)
                .build());
    }

}
