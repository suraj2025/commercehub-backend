package com.commercehub.productImage.dto.response;
public record ProductImageResponse(

        Long id,

        String imageUrl,

        Integer displayOrder,

        Boolean primaryImage

) {
}
