package com.example.Fstock.controller;

import com.example.Fstock.dto.request.AddToCartRequest;
import com.example.Fstock.dto.request.UpdateCartRequest;
import com.example.Fstock.dto.response.ApiResponse;
import com.example.Fstock.dto.response.CartDetailResponse;
import com.example.Fstock.service.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse> addToCart (@RequestBody  AddToCartRequest addToCartRequest) {
        cartService.addToCart(addToCartRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Add to cart successfully")
                .build());

    }
    @GetMapping()
    public ResponseEntity<ApiResponse> getCartDetails(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(ApiResponse.<List<CartDetailResponse>>builder()
                .message("Get cart details successfully")
                .data(cartService.getCartDetails(email))
                .build());
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateCart (@RequestBody List<UpdateCartRequest> updateCartRequests) {
        cartService.updateCart(updateCartRequests);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Update cart successfully")
                .build());

    }



}
