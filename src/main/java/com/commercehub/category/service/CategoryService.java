package com.commercehub.category.service;

import com.commercehub.category.dto.request.CategoryRequest;
import com.commercehub.category.dto.response.CategoryResponse;
import com.commercehub.common.dto.PageResponse;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(
            Long id,
            CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse getCategoryById(Long id);

    PageResponse<CategoryResponse> getAllCategories(
            int page,
            int size,
            String sortBy,
            String direction);
}