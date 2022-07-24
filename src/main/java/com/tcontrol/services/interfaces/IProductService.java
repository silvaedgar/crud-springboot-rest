package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.ProductDTO;
import com.tcontrol.models.Product;

public interface IProductService {

	public ResponseEntity<Map<String, Object>> getAllProducts();
	public ResponseEntity<Map<String, Object>> saveProduct(ProductDTO productDTO, String option);
	public Product getProductById(Long id);
	List<Product> getProductByNameContains(String name);
	
}
