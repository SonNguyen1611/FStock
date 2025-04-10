package com.example.Fstock.responsitory;

import com.example.Fstock.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, Long> {
    boolean existsById(String id);

}
