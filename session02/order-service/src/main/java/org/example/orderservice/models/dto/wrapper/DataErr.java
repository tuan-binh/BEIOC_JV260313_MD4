package org.example.orderservice.models.dto.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DataErr<T> {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private HttpStatus status;
    private T error;
    private String message;
    private String path;
}
