package com.tcontrol.restapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.models.Supplier;
import com.tcontrol.repository.UserRepository;
import com.tcontrol.services.SupplierService;

@RestController
@RequestMapping("/api/t-control/suppliers")
public class SupplierRest {

	@Autowired
	public SupplierService supplierService;

	@Autowired
	public UserRepository userRepository;

	@GetMapping("{id}")
	public Optional<Supplier> getSupplier(@PathVariable (name="id") Long id) {
		return supplierService.getSupplier(id);
	}

	
	@GetMapping
	public List<Supplier> getSuppliers() {
		return supplierService.getSuppliers();
	}
	
	@PostMapping("/{user_id}")
	public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier, @PathVariable (name = "user_id") Long user_id) {
		return new ResponseEntity<>(supplierService.createSupplier(user_id, supplier),HttpStatus.CREATED);
	}
}
