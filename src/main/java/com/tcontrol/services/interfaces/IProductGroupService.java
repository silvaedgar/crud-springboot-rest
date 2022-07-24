package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.ProductGroupDTO;
import com.tcontrol.models.ProductGroup;

public interface IProductGroupService {
	
	public ResponseEntity<Map<String, Object>> saveProductGroup(ProductGroupDTO productGroupDTO, String option);

	public List<ProductGroup> getAllProductGroups();
	public List<ProductGroup> getProductGroupByDescription(String description); 	
	public ProductGroup getProductGroupById(Long id);
//	public List<ProductGroup> getCategoriesByProductGroup(Long group_id);
	
}
