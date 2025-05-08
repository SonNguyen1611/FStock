package com.example.Fstock.responsitory;

import com.example.Fstock.entity.Order;
import com.example.Fstock.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);
    Page<Order> findByOrderIdAndOrderStatus(Integer orderId, OrderStatus orderStatus, Pageable pageable);
    Set<Order> findByEmail(String email);
}
