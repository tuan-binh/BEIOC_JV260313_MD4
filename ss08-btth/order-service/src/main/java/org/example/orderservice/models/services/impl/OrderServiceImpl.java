package org.example.orderservice.models.services.impl;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.clients.ProductClient;
import org.example.orderservice.exceptions.OutOfStockException;
import org.example.orderservice.exceptions.ProductNotfoundException;
import org.example.orderservice.exceptions.ServiceDegradedException;
import org.example.orderservice.models.constants.OrderStatus;
import org.example.orderservice.models.dto.clients.ProductDTO;
import org.example.orderservice.models.dto.req.CreateOrderRequest;
import org.example.orderservice.models.dto.req.OrderDetailRequest;
import org.example.orderservice.models.dto.res.OrderDetailResponse;
import org.example.orderservice.models.dto.res.OrderResponse;
import org.example.orderservice.models.entities.Order;
import org.example.orderservice.models.entities.OrderDetail;
import org.example.orderservice.models.repositories.IOrderDetailRepository;
import org.example.orderservice.models.repositories.IOrderRepository;
import org.example.orderservice.models.services.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final ProductClient productClient;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return List.of();
        }

        Map<Long, List<OrderDetail>> detailsByOrderId = new LinkedHashMap<>();
        orderDetailRepository.findAllByOrderIdIn(orders.stream().map(Order::getId).toList())
                .forEach(detail -> detailsByOrderId
                        .computeIfAbsent(detail.getOrder().getId(), ignored -> new ArrayList<>())
                        .add(detail));

        return orders.stream()
                .map(order -> toResponse(order, detailsByOrderId.getOrDefault(order.getId(), List.of())))
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        Map<Long, Integer> requestedQuantities = mergeDuplicateProducts(request.getOrderDetails());
        Map<Long, ProductDTO> products = new LinkedHashMap<>();
        double total = 0;

        for (Map.Entry<Long, Integer> item : requestedQuantities.entrySet()) {
            ProductDTO product = findById(item.getKey());
            if (product == null || product.getPrice() == null || product.getPrice() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Sản phẩm " + item.getKey() + " không hợp lệ");
            }
            if (product.getStock() == null || product.getStock() < item.getValue()) {
                throw new OutOfStockException("Không đủ số lượng sản phẩm");
            }
            products.put(item.getKey(), product);
            total += product.getPrice() * item.getValue();
        }

        Order order = orderRepository.save(Order.builder()
                .customerName(request.getCustomerName().trim())
                .total(total)
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build());

        List<OrderDetail> details = requestedQuantities.entrySet().stream()
                .map(item -> OrderDetail.builder()
                        .order(order)
                        .productId(item.getKey())
                        .quantity(item.getValue())
                        .unitPrice(products.get(item.getKey()).getPrice())
                        .build())
                .toList();
        details = orderDetailRepository.saveAll(details);
        return toResponse(order, details);
    }

    // Vấn đề: đặt circuit breaker ở createOrder -> bất kỳ ngoại lệ đều sẽ nhảy xuôgns fallback
    // Giải pháp: tách hàm gọi sang product-service và áp cho nó là circuit breaker
    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallbackCreateOrder")
    public ProductDTO findById(Long id) {
        try {
            return productClient.findById(id);
        } catch (Exception e) {
            throw new ProductNotfoundException("Product not foudn");
        }
    }


    private Map<Long, Integer> mergeDuplicateProducts(List<OrderDetailRequest> details) {
        Map<Long, Integer> quantities = new LinkedHashMap<>();
        for (OrderDetailRequest detail : details) {
            try {
                quantities.merge(detail.getProductId(), detail.getQuantity(), Math::addExact);
            } catch (ArithmeticException exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Tổng số lượng sản phẩm vượt quá giới hạn cho phép");
            }
        }
        return quantities;
    }

    private OrderResponse toResponse(Order order, List<OrderDetail> details) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .orderDetails(details.stream()
                        .map(detail -> OrderDetailResponse.builder()
                                .productId(detail.getProductId())
                                .quantity(detail.getQuantity())
                                .unitPrice(detail.getUnitPrice())
                                .build())
                        .toList())
                .build();
    }

    public OrderResponse fallbackCreateOrder(CreateOrderRequest request, Throwable throwable) {
        System.err.println(throwable.getMessage());
        // Xử lý trường dự phòng
        // Câu hỏi đặt ra: nếu như tạo order ở hàm fallback thì sẽ trả về orderresponse dự phòng hay là ném lỗi?
        throw new ServiceDegradedException("Dịch vụ tạo đơn hàng đang không khả dụng, Vui lòng thử lại sau!");
    }
}
