package org.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    // Hướng dẫn về openfeign thay cho rest template

    /*
        viết một dự án microservice:
        tạo ra 2 service về product-service cung cấp sản phẩm danh sách, lấy sản phẩm theo id, và thay đổi số lượng sản phẩm stock
        - order-service (Order và Order Detail) viết ra API tạo đơn hàng createOrder() -> gọi sang service product để call có thể sử dụng openfeign để giao tiếp
        - eureka-server
        - api-gateway
    */
}
