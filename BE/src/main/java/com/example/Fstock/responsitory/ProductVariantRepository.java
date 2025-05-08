package com.example.Fstock.responsitory;

import com.example.Fstock.dto.response.ColorInfo;
import com.example.Fstock.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    ProductVariant findByProductProductIdAndColorNameAndSizeName(Integer productId, String colorName, String sizeName);
    @Query("SELECT DISTINCT new com.example.Fstock.dto.response.ColorInfo(pv.colorName, pv.colorCode)  FROM ProductVariant pv")
    List<ColorInfo> findDistinctColorNameAndColorCode();
    @Query("SELECT DISTINCT pv.sizeName FROM ProductVariant pv")
    List<String > findDistinctSizeName();

}
