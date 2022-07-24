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

import com.tcontrol.DTO.ProductDTO;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.ProductService;

@RestController
@RequestMapping("api/t-control/products/")
public class ProductRest {
	
	@Autowired
	private ProductService productService;

	@Autowired
	ResponseJson returnJsonOK;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long id) {
		return returnJsonOK.messageOK("Datos de Producto Obtenido",productService.getProductById(id));
	}
	
	@GetMapping("/find_by_name/{name}") 
	public ResponseEntity<Map<String, Object>> getProductByDescription(@PathVariable String name) {
		return returnJsonOK.messageOK("Productos cuya descripcion contiene " + name,productService.getProductByNameContains(name));
	}

	@GetMapping("/ordered/{direction}") 
	public ResponseEntity<Map<String, Object>> getProductOrdered(@PathVariable String direction) {
		return returnJsonOK.messageOK("Lista de Productos Ordenados",productService.getProductOrdered(direction));
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		return productService.saveProduct(productDTO, "Create");
	}

	@PutMapping()
	public ResponseEntity<Map<String, Object>> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
		return productService.saveProduct(productDTO, "Update");
	}
	
}
