package com.commercehub.productImage.entity;

import com.commercehub.common.entity.BaseEntity;
import com.commercehub.product.entity.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_images")
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String imageUrl;

    @Column(nullable = false, unique = true)
    private String publicId;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false)
    private Boolean primaryImage = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}