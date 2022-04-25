package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
