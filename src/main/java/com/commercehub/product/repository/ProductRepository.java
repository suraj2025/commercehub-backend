package com.commercehub.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.commercehub.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {
            "category",
            "images"
    })
    Optional<Product> findDetailsByIdAndEnabledTrue(Long id);

    @EntityGraph(attributePaths = {
            "category",
            "images"
    })
    Page<Product> findByEnabledTrue(Pageable pageable);

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);
}