package org.example.categoryservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infomation")
@RequiredArgsConstructor
@RefreshScope
public class AppController {

    @Value("${message-toast}")
    private String message;

    @GetMapping
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok(message);
    }

}
