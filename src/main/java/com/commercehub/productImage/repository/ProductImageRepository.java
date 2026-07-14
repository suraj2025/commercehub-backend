package com.commercehub.productImage.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercehub.productImage.entity.ProductImage;



public interface ProductImageRepository
        extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Long productId);

    Optional<ProductImage> findByPublicId(String publicId);

    Optional<ProductImage> findByProductIdAndPrimaryImageTrue(Long productId);

    long countByProductId(Long productId);
    Optional<ProductImage> findFirstByProductIdOrderByDisplayOrderAsc(Long productId);
    
}
