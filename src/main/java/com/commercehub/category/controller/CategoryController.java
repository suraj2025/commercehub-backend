package com.commercehub.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.commercehub.category.dto.request.CategoryRequest;
import com.commercehub.category.dto.response.CategoryResponse;
import com.commercehub.category.service.CategoryService;
import com.commercehub.common.dto.ApiResponse;
import com.commercehub.common.dto.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create Category (ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Category created successfully",
                        response));
    }

    /**
     * Get All Categories (PUBLIC)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAllCategories(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "name") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        PageResponse<CategoryResponse> response =
                categoryService.getAllCategories(
                        page,
                        size,
                        sortBy,
                        direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Categories fetched successfully",
                        response));
    }

    /**
     * Get Category By Id (PUBLIC)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @PathVariable Long id) {
        System.out.println("Fetching category with ID: " + id);
        CategoryResponse response =
                categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category fetched successfully",
                        response));
    }

    /**
     * Update Category (ADMIN)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response =
                categoryService.updateCategory(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category updated successfully",
                        response));
    }

    /**
     * Delete Category (ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Long id) {
        System.out.println("Deleting category with ID: " + id);
        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category deleted successfully",
                        null));
    }
}