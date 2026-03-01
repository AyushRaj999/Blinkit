package com.blinkit.controller;

import com.blinkit.dto.ProductDtos;
import com.blinkit.entity.Product;
import com.blinkit.exception.ResourceNotFoundException;
import com.blinkit.repository.CategoryRepository;
import com.blinkit.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<Page<Product>> all(@RequestParam(defaultValue = "") String q,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Page<Product> result = q.isBlank()
                ? productRepository.findAll(PageRequest.of(page, size))
                : productRepository.findByNameContainingIgnoreCase(q, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> byId(@PathVariable Long id) {
        return ResponseEntity.ok(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDtos.ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId())))
                .imageUrl(request.getImageUrl())
                .build();
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductDtos.ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId())));
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
