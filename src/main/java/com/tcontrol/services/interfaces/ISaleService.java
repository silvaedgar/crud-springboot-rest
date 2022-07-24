package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.SaleDTO;
import com.tcontrol.models.Sale;


public interface ISaleService {
	
	public List<Map<String, Object>> getAllSalesWithClient();
	List<Map<String, Object>> findSalesByClient(Long client_id);
//	List<Sale> findSalesByClient(Long client_id);
	
	public Sale getSaleById(Long id);
	List<Sale> getSalesPendingsClient(Long client_id);
	
	public ResponseEntity<Map<String, Object>> saveSale(SaleDTO saleDTO);

}
