package org.example.categoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CategoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryServiceApplication.class, args);
    }

    /*
        - xây dựng được hệ thống gồm các yếu tố cốt lõi
            + eureka server
            + api gateway
            + config server
        - xây dựng 2 dịch vụ nhỏ gồm category product
            + category: xây dựng 1 API lấy danh mục theo id
            + product: xây dựng 2 API lấy danh sách sản phẩm và thêm mới sản phẩm
        - đặc biệt lưu ý cấu hình đều phải được lưu trên github
    */

}
