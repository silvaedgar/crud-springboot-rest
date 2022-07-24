package com.tcontrol.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcontrol.models.Supplier;
import com.tcontrol.models.User;
import com.tcontrol.repository.SupplierRepository;
import com.tcontrol.repository.UserRepository;
import com.tcontrol.services.interfaces.ISupplierService;

@Service
public class SupplierService implements ISupplierService {

	@Autowired
	public SupplierRepository supplierRepository; 
	
	@Autowired
	public UserRepository userRepository;
	
	@Override
	public List<Supplier> getSuppliers() {
		// TODO Auto-generated method stub
		return supplierRepository.findAll();
	}

	@Override
	public Supplier createSupplier(Long user_id, Supplier supplier) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(user_id);
		supplier.setUser(user.get());
		return supplierRepository.save(supplier);
	}

	@Override
	public Optional<Supplier> getSupplier(Long id) {
		// TODO Auto-generated method stub
		return supplierRepository.findById(id);
	}
	

}
