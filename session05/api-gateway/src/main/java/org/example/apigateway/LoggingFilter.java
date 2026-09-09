package org.example.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Đây là quá trình về xử lý pre (tiền tố)
        long start = System.currentTimeMillis();
        log.info("Pre Filter {}",start);

        return chain.filter(exchange)
                .doFinally(s -> {
                    // Đây là quá trình xử lý post (hậu tố)
                    long end = System.currentTimeMillis();

                    log.info("Post Filter {}",(end - start));
                });
    }

    @Override
    public int getOrder() {
        return -1; // Cho filter này chạy đầu tiên
    }
}
