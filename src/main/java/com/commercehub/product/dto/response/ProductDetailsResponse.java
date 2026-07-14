package com.commercehub.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.commercehub.productImage.dto.response.ProductImageResponse;

public record ProductDetailsResponse(

        Long id,

        String name,

        String description,

        BigDecimal price,

        Integer stock,

        String brand,

        String sku,

        Long categoryId,

        String categoryName,

        List<ProductImageResponse> images,

        Boolean enabled,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {}