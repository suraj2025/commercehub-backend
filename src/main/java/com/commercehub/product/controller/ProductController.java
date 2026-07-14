package com.commercehub.product.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.commercehub.common.dto.ApiResponse;
import com.commercehub.common.dto.PageResponse;
import com.commercehub.product.dto.request.ProductRequest;
import com.commercehub.product.dto.response.ProductDetailsResponse;
import com.commercehub.product.dto.response.ProductResponse;
import com.commercehub.product.dto.response.ProductSummaryResponse;
import com.commercehub.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create Product (ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> createProduct(
            @Valid @RequestBody ProductRequest request) {

        ProductDetailsResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Product created successfully",
                        response));
    }

    /**
     * Update Product (ADMIN)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        ProductDetailsResponse response =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product updated successfully",
                        response));
    }

    /**
     * Get All Products (PUBLIC)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductSummaryResponse>>> getAllProducts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "name") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        PageResponse<ProductSummaryResponse> response =
                productService.getAllProducts(
                        page,
                        size,
                        sortBy,
                        direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        response));
    }

    /**
     * Get Product By Id (PUBLIC)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> getProductById(
            @PathVariable Long id) {

        ProductDetailsResponse response =
                productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully",
                        response));
    }

    /**
     * Delete Product (ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully",
                        null));
    }
}