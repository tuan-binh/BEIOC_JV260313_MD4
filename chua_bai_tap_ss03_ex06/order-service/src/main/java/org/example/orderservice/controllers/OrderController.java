package org.example.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.models.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final RestTemplate restTemplate;

    // API kiểm tra sản phẩm có tồn tại và lấy thông tin sản phẩm
    @GetMapping("/check-product/{productId}")
    public ResponseEntity<?> checkProduct(@PathVariable Long productId) {

        String url = "http://localhost:8081/api/products/" + productId + "/price";
        ProductDTO product = null;
        try {
            product = restTemplate.getForObject(
                    url, ProductDTO.class
            );
        } catch (Exception e) {
            System.err.println(e.getMessage());
            product = new ProductDTO(0L,"Tên mặc định",new BigDecimal(0),0);
            return ResponseEntity.ok(product);
        }


        return ResponseEntity.ok(product);
    }

}
