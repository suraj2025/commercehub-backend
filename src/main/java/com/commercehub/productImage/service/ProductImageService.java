package com.commercehub.productImage.service;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.commercehub.productImage.dto.response.ProductImageResponse;

public interface ProductImageService {

    List<ProductImageResponse> uploadImages(
            Long productId,
            List<MultipartFile> files) throws IOException;
    List<ProductImageResponse> getProductImages(Long productId);
    void deleteImage(Long imageId) throws IOException;
    void setPrimaryImage(Long imageId);
}
