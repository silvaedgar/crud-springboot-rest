package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.ProductCategoryDTO;
import com.tcontrol.models.ProductCategory;

public interface IProductCategoryService {

	public List<ProductCategory> getAllProductCategories();
	public ResponseEntity<Map<String, Object>> saveProductCategory(ProductCategoryDTO productCategoryDTO, String option);
	public ProductCategory getProductCategoryById(Long id);

}
