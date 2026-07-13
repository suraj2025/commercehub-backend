package com.commercehub.category.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.commercehub.category.dto.request.CategoryRequest;
import com.commercehub.category.dto.response.CategoryResponse;
import com.commercehub.category.entity.Category;
import com.commercehub.category.mapper.CategoryMapper;
import com.commercehub.category.repository.CategoryRepository;
import com.commercehub.category.service.CategoryService;
import com.commercehub.common.dto.PageResponse;
import com.commercehub.common.exception.DuplicateResourceException;
import com.commercehub.common.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByName(request.name())) {
            throw new DuplicateResourceException(
                    "Category",
                    "name",
                    request.name());
        }

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request) {

        Category category = getCategory(id);

        Category duplicateCategory = categoryRepository
                .findByName(request.name())
                .orElse(null);

        if (duplicateCategory != null &&
                !duplicateCategory.getId().equals(id)) {

            throw new DuplicateResourceException(
                    "Category",
                    "name",
                    request.name());
        }

        categoryMapper.updateEntity(category, request);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        // TODO Auto-generated method stub
        Category category = getCategory(id);
        category.setEnabled(false);
        Category deleted = categoryRepository.save(category);
        return;
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        // TODO Auto-generated method stub
        Category category = getCategory(id);
        return categoryMapper.toResponse(category);
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        Page<CategoryResponse> responsePage = categoryPage.map(categoryMapper::toResponse);

        return new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isFirst(),
                responsePage.isLast());
    }

    private Category getCategory(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category",
                        "id",
                        id));
    }

}