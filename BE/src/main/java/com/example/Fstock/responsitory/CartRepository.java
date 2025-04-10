package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserEmail(String email);
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.cartId = :cartId")
    void deleteByCartId(@Param("cartId") int cartId);

}
