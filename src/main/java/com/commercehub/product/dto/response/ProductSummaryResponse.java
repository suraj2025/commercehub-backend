package com.commercehub.product.dto.response;

import java.math.BigDecimal;

public record ProductSummaryResponse(

        Long id,

        String name,
        String description,
        BigDecimal price,

        String brand,

        String thumbnail,

        Long categoryId,

        String categoryName

) {}