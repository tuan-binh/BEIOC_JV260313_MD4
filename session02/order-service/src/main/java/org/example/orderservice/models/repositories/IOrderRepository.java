package org.example.orderservice.models.repositories;

import org.example.orderservice.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IOrderRepository extends JpaRepository<Order,Long> {
}
