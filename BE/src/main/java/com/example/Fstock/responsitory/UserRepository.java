package com.example.Fstock.responsitory;

import com.example.Fstock.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<User> findByEmail(String email);
    User findByUserId(Integer userId);
}
