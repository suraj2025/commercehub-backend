package com.commercehub.product.mapper;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.commercehub.product.dto.request.ProductRequest;
import com.commercehub.product.dto.response.ProductDetailsResponse;
import com.commercehub.product.dto.response.ProductResponse;
import com.commercehub.product.dto.response.ProductSummaryResponse;
import com.commercehub.product.entity.Product;
import com.commercehub.productImage.dto.response.ProductImageResponse;
import com.commercehub.productImage.entity.ProductImage;
import com.commercehub.productImage.mapper.ProductImageMapper;

@Component
public class ProductMapper {

    private final ProductImageMapper productImageMapper;

    public ProductMapper(ProductImageMapper productImageMapper) {
        this.productImageMapper = productImageMapper;
    }

    public Product toEntity(ProductRequest request) {

        Product product = new Product();

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setBrand(request.brand());
        product.setSku(request.sku());

        return product;
    }

    public void updateEntity(Product product, ProductRequest request) {

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setBrand(request.brand());
        product.setSku(request.sku());
    }

    public ProductSummaryResponse toSummaryResponse(Product product) {
        String thumbnail = product.getImages()
        .stream()
        .filter(ProductImage::getPrimaryImage)
        .map(ProductImage::getImageUrl)
        .findFirst()
        .orElse(null);
        return new ProductSummaryResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getBrand(),
                thumbnail,
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
     public ProductDetailsResponse toDetailsResponse(Product product) {
        List<ProductImageResponse> images =
        product.getImages()
                .stream()
                .sorted(
                        Comparator.comparing(
                                ProductImage::getDisplayOrder))
                .map(productImageMapper::toResponse)
                .toList();
        return new ProductDetailsResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getBrand(),
                product.getSku(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                images,
                product.getEnabled(),
                product.getCreatedAt(), 
                product.getUpdatedAt()
        );
    }
}