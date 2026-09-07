package org.example.orderservice.models.dto.req;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.orderservice.models.constants.OrderStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderRequest {
    @NotBlank(message = "Customer Name must be not empty")
    private String customerName;

    @Min(value = 1,message = "Price must be great then 0")
    private Double total;

    private String note;

}
