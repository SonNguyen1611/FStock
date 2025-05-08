package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.priceDefault BETWEEN :price1 AND :price2")
    List<Product> findProductsByPriceRange (@Param("price1")int price1,
                                            @Param("price2")int price2
                                           );
    @Query("select p from Product p join p.productVariants pv where pv.colorName = :colorName")
    List<Product> findByProductVariantsColorName(@Param(("colorName")) String colorName);

    @Query("select p from Product p join p.productVariants pv where pv.sizeName = :sizeName")
    List<Product> findByProductVariantsSizeName(@Param("sizeName") String sizeName);

    @Query("select p from Product p where p.productSKU like %:keyword% or p.productName like %:keyword%")
    List <Product> findBySearchKeyword (@Param("keyword") String searchKeyword);

    List<Product> findByCategoryCategoryId (Integer categoryId);

    Product findByProductId  (Integer id);

    Product findByProductSKU (String productSKU);

}
