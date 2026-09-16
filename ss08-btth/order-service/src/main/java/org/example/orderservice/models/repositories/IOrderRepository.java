package org.example.orderservice.models.repositories;

import org.example.orderservice.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
