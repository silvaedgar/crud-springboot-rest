package com.tcontrol.restapi;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.DTO.ProductGroupDTO;
import com.tcontrol.models.ProductGroup;
import com.tcontrol.repository.ProductGroupRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.ProductGroupService;

@RestController
@RequestMapping("/api/t-control/product-groups/")
public class ProductGroupRest {

	@Autowired
	private ProductGroupService productGroupService;

	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Autowired
	private ResponseJson returnJsonOK;

	@GetMapping("find_categories/{group_id}")
	public ResponseEntity<Map<String, Object>> getCategoriesByProductGroup(@PathVariable Long group_id) {
		List<Map<String, Object>> productCategories = productGroupRepository.getCategoriesByProductGroup(group_id); 
//		return productCategories;
		return returnJsonOK.messageOK("Categorias del Grupo de Producto", productCategories); 		
	}
	
	
	@GetMapping("find_by_description/{description}") 
	public ResponseEntity<Map<String, Object>> getProductGroupByDescription(@PathVariable String description) {
		List<ProductGroup> productGroup = productGroupService.getProductGroupByDescription(description);
		return returnJsonOK.messageOK("Grupo de Productos obtenido con exito", productGroup); 		
	}

	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getProductGroupById(@PathVariable Long id) {
		ProductGroup productGroup = productGroupService.getProductGroupById(id);
		return returnJsonOK.messageOK("Grupo de Producto obtenido con exito", productGroup); 
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllProductGroups() {
		List<ProductGroup> productGroups = productGroupService.getAllProductGroups();
		return returnJsonOK.messageOK("Listado de Grupos de Producto obtenido con exito", productGroups); 
		
	}

	@PostMapping()
	public ResponseEntity<Map<String, Object>> createProductGroup(@Valid @RequestBody ProductGroupDTO productGroupDTO) {
		ResponseEntity<Map<String, Object>> responseEntity = productGroupService.saveProductGroup(productGroupDTO, "Create");
		return responseEntity;
	}

	@PutMapping()
	public ResponseEntity<Map<String, Object>> updateProductGroup(@Valid @RequestBody ProductGroupDTO productGroupDTO) {
		ResponseEntity<Map<String, Object>> responseEntity = productGroupService.saveProductGroup(productGroupDTO, "Update");
		return responseEntity;
	}

}
