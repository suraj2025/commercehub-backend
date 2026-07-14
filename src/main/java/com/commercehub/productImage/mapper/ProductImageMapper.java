package com.commercehub.productImage.mapper;

import org.springframework.stereotype.Component;

import com.commercehub.productImage.dto.response.ProductImageResponse;
import com.commercehub.productImage.entity.ProductImage;

@Component
public class ProductImageMapper {

    public ProductImageResponse toResponse(
            ProductImage image) {

        return new ProductImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getDisplayOrder(),
                image.getPrimaryImage()
        );
    }

}