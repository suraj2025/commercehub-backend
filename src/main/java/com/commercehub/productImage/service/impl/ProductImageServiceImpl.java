package com.commercehub.productImage.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.commercehub.cloudinary.dto.CloudinaryResponse;
import com.commercehub.cloudinary.service.CloudinaryService;
import com.commercehub.common.exception.ResourceNotFoundException;
import com.commercehub.product.entity.Product;
import com.commercehub.product.repository.ProductRepository;
import com.commercehub.productImage.dto.response.ProductImageResponse;
import com.commercehub.productImage.entity.ProductImage;
import com.commercehub.productImage.mapper.ProductImageMapper;
import com.commercehub.productImage.repository.ProductImageRepository;
import com.commercehub.productImage.service.ProductImageService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductImageServiceImpl
        implements ProductImageService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final CloudinaryService cloudinaryService;

    private final ProductImageMapper productImageMapper;

    public ProductImageServiceImpl(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            CloudinaryService cloudinaryService,
            ProductImageMapper productImageMapper) {

        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.cloudinaryService = cloudinaryService;
        this.productImageMapper = productImageMapper;
    }

    @Override
    public List<ProductImageResponse> uploadImages(
            Long productId,
            List<MultipartFile> files)
            throws IOException {

        Product product = getProduct(productId);
        // validation
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload at least one image.");
        }

        // count
        int displayOrder = (int) productImageRepository.countByProductId(productId);

        boolean primaryExists = productImageRepository
                .findByProductIdAndPrimaryImageTrue(productId)
                .isPresent();
        List<ProductImage> images = new ArrayList<>();

        List<String> uploadedPublicIds = new ArrayList<>();
        try {

            // upload
            for (MultipartFile file : files) {
                CloudinaryResponse response = cloudinaryService.uploadImage(file);
                uploadedPublicIds.add(response.publicId());
                ProductImage image = new ProductImage();

                image.setImageUrl(response.url());

                image.setPublicId(response.publicId());

                image.setProduct(product);

                displayOrder++;

                image.setDisplayOrder(displayOrder);
                if (!primaryExists) {

                    image.setPrimaryImage(true);

                    primaryExists = true;
                }
                images.add(image);
            }
            // save
            List<ProductImage> savedImages = productImageRepository.saveAll(images);
            // response
            return savedImages
                    .stream()
                    .map(productImageMapper::toResponse)
                    .toList();

        } catch (Exception ex) {

            for (String publicId : uploadedPublicIds) {

                try {

                    cloudinaryService.deleteImage(publicId);

                } catch (Exception ignored) {
                }
            }

            throw ex;
        }

    }

    @Override
    public List<ProductImageResponse> getProductImages(Long productId) {

        getProduct(productId);

        return productImageRepository
                .findByProductIdOrderByDisplayOrderAsc(productId)
                .stream()
                .map(productImageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) throws IOException {

        ProductImage image = getImage(imageId);

        Long productId = image.getProduct().getId();

        boolean wasPrimary = image.getPrimaryImage();

        cloudinaryService.deleteImage(image.getPublicId());

        productImageRepository.delete(image);

        if (wasPrimary) {

            productImageRepository
                    .findFirstByProductIdOrderByDisplayOrderAsc(productId)
                    .ifPresent(next -> {

                        next.setPrimaryImage(true);

                        productImageRepository.save(next);

                    });
        }
    }

    @Override
    public void setPrimaryImage(Long imageId) {

        ProductImage selectedImage = getImage(imageId);

        if (selectedImage.getPrimaryImage()) {
            return;
        }

        Long productId = selectedImage.getProduct().getId();

        productImageRepository
                .findByProductIdAndPrimaryImageTrue(productId)
                .ifPresent(current -> {

                    current.setPrimaryImage(false);

                });

        selectedImage.setPrimaryImage(true);

    }

    private ProductImage getImage(Long imageId) {

        return productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductImage",
                        "id",
                        imageId));
    }

    private Product getProduct(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product",
                        "id",
                        id));
    }

}
