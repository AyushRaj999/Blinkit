package com.blinkit.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

public class ProductDtos {
    @Data
    public static class ProductRequest {
        @NotBlank private String name;
        private String description;
        @NotNull @DecimalMin("0.0") private BigDecimal price;
        @NotNull @Min(0) private Integer stock;
        @NotNull private Long categoryId;
        private String imageUrl;
    }
}
