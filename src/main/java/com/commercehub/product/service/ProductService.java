package com.commercehub.product.service;

import com.commercehub.common.dto.PageResponse;
import com.commercehub.product.dto.request.ProductRequest;
import com.commercehub.product.dto.response.ProductDetailsResponse;
import com.commercehub.product.dto.response.ProductSummaryResponse;

public interface ProductService {

    ProductDetailsResponse createProduct(ProductRequest request);

    ProductDetailsResponse updateProduct(
            Long id,
            ProductRequest request);

    void deleteProduct(Long id);

    ProductDetailsResponse getProductById(Long id);

    PageResponse<ProductSummaryResponse> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction
    );
}
