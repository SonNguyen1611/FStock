package com.example.Fstock.responsitory;

import com.example.Fstock.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    ProductVariant findByProductProductIdAndColorColorNameAndSizeSizeName(Integer productId, String colorName, String sizeName);

}
