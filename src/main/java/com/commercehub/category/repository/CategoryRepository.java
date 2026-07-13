package com.commercehub.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercehub.category.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    Optional<Category> findByName(
            String name);
}