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

    @Query("select p from Product p " +
            "JOIN  p.productVariants pv " +
            "JOIN pv.color c  where c.id = :colorId ")
    List<Product> findProductsByColor( @Param("colorId") Integer colorId);

    @Query("select p from Product p " +
            "JOIN  p.productVariants pv " +
            "JOIN pv.size s  where s.sizeId = :sizeId")
    List<Product> findProductsBySize(@Param("sizeId") Integer sizeId);

    List<Product> findByCategoryCategoryId (Integer categoryId);

    Product findByProductId  (Integer id);


}
