package org.example.orderservice.models.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailResponse {
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
}
