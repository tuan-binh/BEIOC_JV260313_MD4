package com.example.notiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NotiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotiServiceApplication.class, args);
    }

}
