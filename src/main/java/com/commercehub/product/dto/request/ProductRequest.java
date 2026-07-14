package com.commercehub.product.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequest(

        @NotBlank(message = "Product name is required")
        @Size(max = 200)
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stock,

        @NotBlank(message = "Brand is required")
        @Size(max = 100)
        String brand,

        @NotBlank(message = "SKU is required")
        @Size(max = 100)
        String sku,

        @NotNull(message = "Category is required")
        Long categoryId

) {}
