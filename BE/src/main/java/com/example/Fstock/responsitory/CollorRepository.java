package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollorRepository extends JpaRepository<Color, Integer> {
}
