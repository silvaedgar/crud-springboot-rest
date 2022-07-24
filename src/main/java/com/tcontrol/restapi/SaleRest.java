package com.tcontrol.restapi;


import java.util.Map;

import javax.validation.Valid;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.DTO.SaleDTO;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.ClientService;
import com.tcontrol.services.CoinService;
import com.tcontrol.services.SaleService;

@RestController
@RequestMapping("/api/t-control/sales/")
public class SaleRest {

	@Autowired
	public CoinService coinService;
	
	@Autowired
	public ClientService clientService;

	
	@Autowired
	public SaleService saleService;

	@Autowired
	public ResponseJson returnJsonOK;
	
//	private final Logger log = (Logger) LoggerFactory.logger(getClass());
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllSale() {		
		return returnJsonOK.messageOK("Listado de Facturas de Ventas", saleService.getAllSalesWithClient());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getSale(@PathVariable Long id) {
		return returnJsonOK.messageOK("Datos de Factura obtenida del Cliente " + id, saleService.getSaleById(id));
	}
	
	@GetMapping("/pendings/{client_id}")
	public ResponseEntity<Map<String, Object>> getSalesPendientes(@PathVariable Long client_id) {
		return returnJsonOK.messageOK("Facturas Pendientes del Cliente", saleService.getSalesPendingsClient(client_id));
	}

	
	@GetMapping("/by_clients/{client_id}") 
	public ResponseEntity<Map<String, Object>> getSalesByClient (@PathVariable Long client_id) {
		return returnJsonOK.messageOK("Listado de Facturas del Cliente con ID " + client_id,saleService.findSalesByClient(client_id));	
//		return returnJsonOK.messageOK("Listado de Facturas del Cliente con ID " + client_id,saleService.findByClient(client_id));	
		
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveSale(@Valid @RequestBody SaleDTO saleDTO) {
		return saleService.saveSale(saleDTO);
	}

}
