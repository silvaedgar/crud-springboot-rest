package com.tcontrol.restapi;

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

import com.tcontrol.DTO.ProductCategoryDTO;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.ProductCategoryService;

@RestController
@RequestMapping("/api/t-control/product-categories/")
public class ProductCategoryRest {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	ResponseJson returnJsonOK;
	
	@GetMapping("/ordered/{direction}")
	public ResponseEntity<Map<String, Object>> getProductCategoryOrdered(@PathVariable String direction) {
		return returnJsonOK.messageOK("Listado de Categoria de Productos Obtenidas Ordenamente", productCategoryService.getProductCategoryOrdered(direction));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getProductCategory(@PathVariable Long id) {
		return returnJsonOK.messageOK("Categoria de Producto Obtenida", productCategoryService.getProductCategoryById(id));
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getProductCategories() {
		return returnJsonOK.messageOK("Listado de Categorias de Producto Obtenida", productCategoryService.getAllProductCategories());
	}

	@PostMapping()
	public ResponseEntity<Map<String, Object>> createProductCategory(@Valid @RequestBody ProductCategoryDTO productCategoryDTO) {
		return productCategoryService.saveProductCategory(productCategoryDTO, "Create");
	}

	@PutMapping()
	public ResponseEntity<Map<String, Object>> updateProductCategory(@Valid @RequestBody ProductCategoryDTO productCategoryDTO) {
		return productCategoryService.saveProductCategory(productCategoryDTO, "Update");
	}

}
