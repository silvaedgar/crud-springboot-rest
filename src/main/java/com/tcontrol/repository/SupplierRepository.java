package com.tcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcontrol.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
