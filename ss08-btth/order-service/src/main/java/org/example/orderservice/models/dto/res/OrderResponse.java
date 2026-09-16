package org.example.orderservice.models.dto.res;

import lombok.Builder;
import lombok.Data;
import org.example.orderservice.models.constants.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String customerName;
    private Double total;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private List<OrderDetailResponse> orderDetails;
}
