package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.ProductGroupDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.ProductGroup;
import com.tcontrol.models.User;
import com.tcontrol.repository.ProductGroupRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.interfaces.IProductGroupService;

@Service
public class ProductGroupService implements IProductGroupService {

	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResponseJson returnJsonOK;
	
	@Override
	public List<ProductGroup> getProductGroupByDescription(String description) {
		// TODO Auto-generated method stub
		return productGroupRepository.findByDescriptionContains(description);
	}
	
	@Override
	public ProductGroup getProductGroupById(Long id) {
		// TODO Auto-generated method stub
		ProductGroup productGroup = productGroupRepository.findById(id)
				.orElseThrow(() -> new HandlerExceptions("ID: " + id + " de Grupo de Producto no existe", HttpStatus.NOT_FOUND));
		return productGroup;
	}
	
	@Override
	public List<ProductGroup> getAllProductGroups() {
		// TODO Auto-generated method stub
		return productGroupRepository.findAll();
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> saveProductGroup(ProductGroupDTO productGroupDTO, String option) {
		
// No se usa el model Mapper. Se puede usar pero solo son dos campos		
		ProductGroup productGroup = new ProductGroup();
		productGroup.setDescription(productGroupDTO.getDescription());
		productGroup.setUpdatedAt(LocalDateTime.now());
		
		User user = userService.getUserById(productGroupDTO.getUserId());
		productGroup.setUser(user);

		switch (option) {
		case "Create":
			productGroup.setCreatedAt(LocalDateTime.now());
			productGroup.setStatus("Activo");
			break;
		default:			
			ProductGroup productGroupCurrent = getProductGroupById(productGroupDTO.getId());
			productGroup.setId(productGroupCurrent.getId());
			productGroup.setCreatedAt(productGroupCurrent.getCreatedAt());
			productGroup.setStatus(productGroupCurrent.getStatus());
			break;
		}
//		
		try {
			ProductGroup productGroupSave = productGroupRepository.save(productGroup);
			return returnJsonOK.messageOK("Grupo de Producto " + productGroup.getDescription() + ". "
							+ (option=="Create"? "Creado con exito" : "Actualizado con exito"), productGroupSave);
		} catch (Exception e) {
			throw new HandlerExceptions("Verifique los datos de entrada", HttpStatus.BAD_REQUEST);
		}		
	}

}
