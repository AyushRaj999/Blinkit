package com.blinkit.controller;

import com.blinkit.dto.CartDtos;
import com.blinkit.entity.Cart;
import com.blinkit.entity.CartItem;
import com.blinkit.exception.ResourceNotFoundException;
import com.blinkit.repository.CartRepository;
import com.blinkit.repository.ProductRepository;
import com.blinkit.service.CommonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CommonService commonService;

    @GetMapping
    public ResponseEntity<Cart> get() {
        var user = commonService.currentUser();
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> add(@Valid @RequestBody CartDtos.CartRequest request) {
        Cart cart = get().getBody();
        CartItem existing = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(request.getProductId())).findFirst().orElse(null);
        if (existing != null) existing.setQuantity(existing.getQuantity() + request.getQuantity());
        else cart.getItems().add(CartItem.builder()
                .cart(cart)
                .product(productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId())))
                .quantity(request.getQuantity())
                .build());
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> update(@Valid @RequestBody CartDtos.CartRequest request) {
        Cart cart = get().getBody();
        cart.getItems().stream().filter(i -> i.getProduct().getId().equals(request.getProductId())).findFirst().ifPresent(i -> i.setQuantity(request.getQuantity()));
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> remove(@RequestParam Long productId) {
        Cart cart = get().getBody();
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        return ResponseEntity.ok(cartRepository.save(cart));
    }
}
