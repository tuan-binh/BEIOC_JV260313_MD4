package org.example.productservice.models.dto.req;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.productservice.models.entities.Category;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductReq {
    private String name;
    private Double price;
    private Integer stock;
    private Long categoryId;
}
