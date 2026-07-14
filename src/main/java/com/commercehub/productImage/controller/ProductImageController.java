package com.commercehub.productImage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.commercehub.common.dto.ApiResponse;
import com.commercehub.productImage.dto.response.ProductImageResponse;
import com.commercehub.productImage.service.ProductImageService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    /**
     * Upload Images
     */
    @PostMapping("/{productId}/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> uploadImages(

            @PathVariable Long productId,

            @RequestParam("files") List<MultipartFile> files)

            throws IOException {

        List<ProductImageResponse> response = productImageService.uploadImages(
                productId,
                files);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Images uploaded successfully",
                        response));
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getProductImages(
            @PathVariable Long productId) {

        List<ProductImageResponse> response = productImageService.getProductImages(productId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Images fetched successfully",
                        response));
    }

    @DeleteMapping("/images/{imageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteImage(
            @PathVariable Long imageId)
            throws IOException {

        productImageService.deleteImage(imageId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Image deleted successfully",
                        null));
    }

    @PutMapping("/images/{imageId}/primary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> setPrimaryImage(
            @PathVariable Long imageId) {

        productImageService.setPrimaryImage(imageId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Primary image updated successfully",
                        null));
    }

}