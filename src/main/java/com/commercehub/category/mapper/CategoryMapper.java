package com.commercehub.category.mapper;

import org.springframework.stereotype.Component;

import com.commercehub.category.dto.request.CategoryRequest;
import com.commercehub.category.dto.response.CategoryResponse;
import com.commercehub.category.entity.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.name());
        category.setDescription(request.description());

        return category;
    }

    public CategoryResponse toResponse(Category category) {

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getEnabled(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }

     public void updateEntity(
            Category category,
            CategoryRequest request) {

        category.setName(request.name());
        category.setDescription(request.description());
    }
}