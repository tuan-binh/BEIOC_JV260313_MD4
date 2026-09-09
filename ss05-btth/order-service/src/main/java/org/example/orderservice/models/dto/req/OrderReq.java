package org.example.orderservice.models.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderReq {
    private String customerName;
    private List<OrderDetailReq> orderDetails;
}
