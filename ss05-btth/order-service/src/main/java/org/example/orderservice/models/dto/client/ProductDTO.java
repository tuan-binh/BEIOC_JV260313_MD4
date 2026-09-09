package org.example.orderservice.models.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;
}
