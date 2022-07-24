package com.tcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcontrol.models.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
