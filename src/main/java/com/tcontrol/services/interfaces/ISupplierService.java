package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.tcontrol.models.Supplier;

public interface ISupplierService {

	public List<Supplier> getSuppliers();
	
	public Supplier createSupplier(Long user_id, Supplier supplier);
	
	public Optional<Supplier> getSupplier (Long id);
}
