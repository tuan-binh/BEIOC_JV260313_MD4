package org.example.productservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "tên dịch vụ - tên trên eureka-service của service")
public interface UserClient {

    @GetMapping("/endpoint ghi như nào thì ghi vào đây đúng như thế")
    String getAll();


}
