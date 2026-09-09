package org.example.orderservice.models.services;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.clients.ProductClient;
import org.example.orderservice.models.dto.client.ProductDTO;
import org.example.orderservice.models.dto.req.OrderDetailReq;
import org.example.orderservice.models.dto.req.OrderReq;
import org.example.orderservice.models.entities.Order;
import org.example.orderservice.models.entities.OrderDetail;
import org.example.orderservice.models.repositories.IOrderDetailRepository;
import org.example.orderservice.models.repositories.IOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Transactional
    public Order createOrder(OrderReq req) {
        validateRequest(req);

        // Gộp các dòng trùng sản phẩm để kiểm tra và cập nhật tồn kho chính xác một lần.
        Map<Long, Integer> requestedQuantities = new LinkedHashMap<>();
        for (OrderDetailReq detail : req.getOrderDetails()) {
            if (detail == null || detail.getProductId() == null) {
                throw new IllegalArgumentException("Mã sản phẩm không được để trống");
            }
            if (detail.getQuantity() == null || detail.getQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Số lượng sản phẩm " + detail.getProductId() + " phải lớn hơn 0"
                );
            }

            requestedQuantities.merge(detail.getProductId(), detail.getQuantity(), (current, added) -> {
                try {
                    return Math.addExact(current, added);
                } catch (ArithmeticException exception) {
                    throw new IllegalArgumentException("Tổng số lượng sản phẩm vượt quá giới hạn cho phép");
                }
            });
        }

        Map<Long, ProductDTO> products = new LinkedHashMap<>();
        BigDecimal total = BigDecimal.ZERO;

        // Kiểm tra toàn bộ đơn trước khi thay đổi tồn kho để hạn chế cập nhật dở dang.
        for (Map.Entry<Long, Integer> item : requestedQuantities.entrySet()) {
            ProductDTO product = productClient.findById(item.getKey());
            if (product == null) {
                throw new IllegalArgumentException("Không tìm thấy sản phẩm có id = " + item.getKey());
            }
            if (product.getPrice() == null || product.getPrice().signum() < 0) {
                throw new IllegalStateException("Giá sản phẩm " + item.getKey() + " không hợp lệ");
            }
            if (product.getQuantity() == null || product.getQuantity() < item.getValue()) {
                throw new IllegalArgumentException(
                        "Sản phẩm " + item.getKey() + " không đủ tồn kho. Còn lại: "
                                + (product.getQuantity() == null ? 0 : product.getQuantity())
                );
            }

            products.put(item.getKey(), product);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getValue())));
        }

        Order order = orderRepository.save(Order.builder()
                .customerName(req.getCustomerName().trim())
                .total(total.doubleValue())
                .createdAt(LocalDateTime.now())
                .build());

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Map.Entry<Long, Integer> item : requestedQuantities.entrySet()) {
            ProductDTO product = products.get(item.getKey());
            int remainingQuantity = product.getQuantity() - item.getValue();

            productClient.changeProduct(item.getKey(), remainingQuantity);
            orderDetails.add(OrderDetail.builder()
                    .order(order)
                    .productId(item.getKey())
                    .quantity(item.getValue())
                    .unitPrice(product.getPrice().doubleValue())
                    .build());
        }

        orderDetailRepository.saveAll(orderDetails);
        return order;
    }

    private void validateRequest(OrderReq req) {
        if (req == null) {
            throw new IllegalArgumentException("Thông tin đơn hàng không được để trống");
        }
        if (req.getCustomerName() == null || req.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (req.getOrderDetails() == null || req.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("Đơn hàng phải có ít nhất một sản phẩm");
        }
    }
}
