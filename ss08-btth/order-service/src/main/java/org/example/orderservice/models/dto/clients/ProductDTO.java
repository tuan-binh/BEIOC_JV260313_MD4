package org.example.orderservice.models.dto.clients;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
}
