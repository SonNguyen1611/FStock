package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Category;
import com.example.Fstock.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCategoryNameContaining ( String searchKeyword);
}
