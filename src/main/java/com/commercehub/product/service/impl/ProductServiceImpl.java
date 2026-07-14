package com.commercehub.product.service.impl;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commercehub.category.entity.Category;
import com.commercehub.category.repository.CategoryRepository;
import com.commercehub.common.dto.PageResponse;
import com.commercehub.common.exception.DuplicateResourceException;
import com.commercehub.common.exception.ResourceNotFoundException;
import com.commercehub.product.dto.request.ProductRequest;
import com.commercehub.product.dto.response.ProductDetailsResponse;
import com.commercehub.product.dto.response.ProductSummaryResponse;
import com.commercehub.product.entity.Product;
import com.commercehub.product.mapper.ProductMapper;
import com.commercehub.product.repository.ProductRepository;
import com.commercehub.product.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDetailsResponse createProduct(ProductRequest request) {

        if (productRepository.existsBySku(request.sku())) {
            throw new DuplicateResourceException(
                    "Product",
                    "sku",
                    request.sku());
        }

        Category category = getCategory(request.categoryId());

        Product product = productMapper.toEntity(request);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDetailsResponse(savedProduct);
    }

    @Override
    public ProductDetailsResponse updateProduct(Long id, ProductRequest request) {

        Product product = getProduct(id);

        Optional<Product> duplicate = productRepository.findBySku(request.sku());

        if (duplicate.isPresent()
                && !duplicate.get().getId().equals(id)) {

            throw new DuplicateResourceException(
                    "Product",
                    "sku",
                    request.sku());
        }

        Category category = getCategory(request.categoryId());

        productMapper.updateEntity(product, request);
        product.setCategory(category);

        return productMapper.toDetailsResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = getProduct(id);

        product.setEnabled(false);

        // No save() required.
        // Hibernate Dirty Checking will update the entity.
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailsResponse getProductById(Long id) {

        Product product = getProductDetails(id);

        return productMapper.toDetailsResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductSummaryResponse> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage =
                productRepository.findByEnabledTrue(pageable);

        Page<ProductSummaryResponse> responsePage =
                productPage.map(productMapper::toSummaryResponse);

        return new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isFirst(),
                responsePage.isLast());
    }

    /**
     * Used by Create & Update
     */
    private Category getCategory(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "id",
                                id));
    }

    /**
     * Lightweight Product Fetch
     * Used for Update/Delete
     */
    private Product getProduct(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "id",
                                id));
    }

    /**
     * Product Details Fetch
     * Loads Category + Images
     * Used by GET /products/{id}
     */
    private Product getProductDetails(Long id) {

        return productRepository
                .findDetailsByIdAndEnabledTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "id",
                                id));
    }
}