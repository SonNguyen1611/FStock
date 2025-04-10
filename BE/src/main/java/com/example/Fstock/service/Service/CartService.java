package com.example.Fstock.service.Service;

import com.example.Fstock.dto.request.AddToCartRequest;
import com.example.Fstock.dto.request.UpdateCartRequest;
import com.example.Fstock.dto.response.CartDetailResponse;

import java.util.List;

public interface CartService {
    void addToCart(AddToCartRequest addToCartRequest);
    List<CartDetailResponse> getCartDetails(String email);
    void updateCart(List<UpdateCartRequest> updateCartRequest);

}
