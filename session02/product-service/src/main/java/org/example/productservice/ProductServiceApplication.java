package org.example.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

    /*
        Mục tiêu:
        - Hiểu về kiến trúc của 1 microservice
        - Sử dụng được Mapper
        Bài toán:
        - API về danh sách sản phẩm
        - API thêm mới sản phẩm (bắt buộc phải DTO)
    */
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
