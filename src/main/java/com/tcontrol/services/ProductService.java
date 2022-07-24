package com.tcontrol.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.ProductDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.Product;
import com.tcontrol.models.ProductCategory;
import com.tcontrol.models.User;
import com.tcontrol.repository.ProductRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService{

	@Autowired
	public ProductRepository productRepository;
	
	@Autowired
	public UserService userService;

	@Autowired
	public ProductCategoryService productCategoryService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ResponseJson returnJsonOK;
	
	@Override
	public ResponseEntity<Map<String, Object>> saveProduct(ProductDTO productDTO, String option) {
		// TODO Auto-generated method stub
		Product product = new Product();
		product = modelMapper.map(productDTO, Product.class);
		product.setUpdatedAt(LocalDateTime.now());		
		User user = userService.getUserById(productDTO.getUserId());
		product.setUser(user);
		ProductCategory productCategory = productCategoryService.getProductCategoryById(productDTO.getCategoryId());
		product.setProductCategory(productCategory);		
		switch (option) {
		case "Create":
			product.setCreatedAt(LocalDateTime.now());
			product.setStatus("Activo");
			product.setAvgCost(0.0);
			product.setStock(0.0);
			break;
		default:
			Product productCurrent = getProductById(productDTO.getId());
			product.setId(productCurrent.getId());
			product.setCreatedAt(productCurrent.getCreatedAt());
			product.setStatus(productCurrent.getStatus());
			product.setAvgCost(productCurrent.getAvgCost());
			product.setStock(productCurrent.getStock());			
			break;
		}
		
		try {
			Product productSave = productRepository.save(product);
			return returnJsonOK.messageOK("Producto " + product.getName() + ". "
					+ (option == "Create" ? "Creado con exito" : "Actualizado con exito"), productSave);			
		} catch (Exception e) {
			throw new HandlerExceptions("Error en Datos de Entrada: " + e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override	
	public ResponseEntity<Map<String, Object>> getAllProducts() {
		return returnJsonOK.messageOK("Listado de Productos", productRepository.findAll());
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElseThrow(() -> new HandlerExceptions("ID " + id + " de producto no registrado", HttpStatus.NOT_FOUND));
	}

	@Override
	public List<Product> getProductByNameContains(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByNameContaining(name);
	}

	public List<Product> getProductOrdered(String direction) {
		if (direction.compareTo("ASC") != 0)
			return productRepository.findAll(Sort.by("name").descending());
		return productRepository.findAll(Sort.by("name").ascending());
	}
}
