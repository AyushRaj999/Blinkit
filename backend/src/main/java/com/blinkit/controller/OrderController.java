package com.blinkit.controller;

import com.blinkit.entity.*;
import com.blinkit.repository.CartRepository;
import com.blinkit.repository.OrderRepository;
import com.blinkit.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CommonService commonService;

    @PostMapping
    public ResponseEntity<Order> create() {
        User user = commonService.currentUser();
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow();
        Order order = Order.builder().user(user).status(OrderStatus.PENDING).paymentStatus("PENDING").build();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            order.getItems().add(OrderItem.builder().order(order).product(item.getProduct()).quantity(item.getQuantity()).price(item.getProduct().getPrice()).build());
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setTotalPrice(total);
        cart.getItems().clear();
        cartRepository.save(cart);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Order>> my() {
        User user = commonService.currentUser();
        return ResponseEntity.ok(orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> all() { return ResponseEntity.ok(orderRepository.findAll()); }
}
