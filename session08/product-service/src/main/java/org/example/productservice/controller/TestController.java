package org.example.productservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.example.productservice.clients.CategoryClient;
import org.example.productservice.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final CategoryClient categoryClient;

    // Keycounter -> đếm số lần thử lại trong bài toán retry

    @GetMapping("/categories/{id}")
    @CircuitBreaker(name = "findByIdCircuitBreaker",fallbackMethod = "fallbackFindById")
    // Muốn sử dụng retry --> @Retry(name="")
    @Retry(name = "findByIdRetry",fallbackMethod = "fallbackFindByIdRetry")
    // Muốn sử dụng rate limiter giới hạn request nhận vào --> @RateLimiter(name,fallback)
    @RateLimiter(name = "findByIdRateLimter",fallbackMethod = "fallbackFindByIdRateLimiter")
    public ResponseEntity<?> testCircuitBreakers(@PathVariable Long id) {
        return ResponseEntity.ok(categoryClient.findById(id));
    }

    public ResponseEntity<?> fallbackFindById(@PathVariable Long id,Throwable throwable) {
        // Xây dựng 1 trường hợp dự phòng
        // Trả về kết quả dự phòng tránh client lõi trắng
        // Trả về dữ liệu default

        System.err.println(throwable.getMessage());

        CategoryDTO fallbackCategory = new CategoryDTO();
        fallbackCategory.setId(0L);
        fallbackCategory.setName("Danh mục mặc định");

        return ResponseEntity.ok(fallbackCategory);
    }

    public ResponseEntity<?> fallbackFindByIdRetry(@PathVariable Long id,Throwable throwable) {
        System.err.println(throwable.getMessage());
        CategoryDTO fallbackCategory = new CategoryDTO();
        fallbackCategory.setId(0L);
        fallbackCategory.setName("Danh mục mặc định");
        return ResponseEntity.ok(fallbackCategory);
    }

    public ResponseEntity<?> fallbackFindByIdRateLimiter(@PathVariable Long id,Throwable throwable) {
        System.err.println(throwable.getMessage());
        CategoryDTO fallbackCategory = new CategoryDTO();
        fallbackCategory.setId(0L);
        fallbackCategory.setName("Danh mục mặc định");
        return ResponseEntity.ok(fallbackCategory);
    }

/**
 * 2 service dịch vụ eureka và api gateway để gọi tập trung 1 nơi cổng 8080
 * 2 service con là product và order
 *      - product: API - lấy sản phẩm theo id
 *      - order: API - thêm mới đơn hàng
 *               API - lấy danh sách đơn hàng
 *      (order - phải sử dụng circuit breaker khi mà product lỗi)
 *      - Kịch bản test:
 *      - TH1: Thành công
 *      - TH2: sản phẩm hết hàng thì ném ngoại lệ
 *      - TH3: service product không còn chạy
 * */
}
