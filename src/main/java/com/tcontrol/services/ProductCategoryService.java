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

import com.tcontrol.DTO.ProductCategoryDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.ProductCategory;
import com.tcontrol.models.User;
import com.tcontrol.repository.ProductCategoryRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.interfaces.IProductCategoryService;

@Service
public class ProductCategoryService implements IProductCategoryService {

	@Autowired
	UserService userService;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ResponseJson returnJsonOK;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ProductCategory> getAllProductCategories() {
		// TODO Auto-generated method stub		
		return productCategoryRepository.findAll();
	}
	
	@Override
	public ProductCategory getProductCategoryById(Long id) {
		return productCategoryRepository.findById(id)
				.orElseThrow(() -> new HandlerExceptions("ID : " + id + " de Categorias de Productos no existe", HttpStatus.NOT_FOUND));
	}

	public List<ProductCategory>  getProductCategoryOrdered(String direction) {		
		if (direction.compareTo("ASC") != 0)
			return productCategoryRepository.findAll(Sort.by("description").descending());
		return productCategoryRepository.findAll(Sort.by("description").ascending());
	}
	
	
	public ResponseEntity<Map<String, Object>> saveProductCategory(ProductCategoryDTO productCategoryDTO, String option) {

		ProductCategory productCategory = new ProductCategory();
		productCategory = modelMapper.map(productCategoryDTO, ProductCategory.class);
//		productCategory.setDescription(productCategoryDTO.getDescription());
//		productCategory.setProductGroup(productGroupService.getProductGroupById(productCategoryDTO.getGroupId()));
		User user = userService.getUserById(productCategoryDTO.getUserId());
		productCategory.setUser(user);
		productCategory.setUpdatedAt(LocalDateTime.now());
		
		switch (option) {
		case "Create":
			productCategory.setCreatedAt(LocalDateTime.now());
			productCategory.setStatus("Activo");
			break;
		default:
			ProductCategory productCategoryCurrent = getProductCategoryById(productCategoryDTO.getId());
			productCategory.setId(productCategoryCurrent.getId());
			productCategory.setCreatedAt(productCategoryCurrent.getCreatedAt());
			productCategory.setStatus(productCategoryCurrent.getStatus());
			break;
		}

		try {
			return returnJsonOK.messageOK("Categoria de Producto " + productCategory.getDescription() + ". "
					+ (option == "Create" ? "Creado con exito" : "Actualizado con exito"), productCategoryRepository.save(productCategory));
		} catch (Exception e) {
			throw new HandlerExceptions("Verifique los datos de entrada" + e.getLocalizedMessage() , HttpStatus.BAD_REQUEST);
		}
	}
}
