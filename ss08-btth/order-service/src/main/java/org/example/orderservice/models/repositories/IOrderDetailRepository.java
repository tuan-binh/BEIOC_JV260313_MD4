package org.example.orderservice.models.repositories;

import org.example.orderservice.models.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrderIdIn(Collection<Long> orderIds);
}
