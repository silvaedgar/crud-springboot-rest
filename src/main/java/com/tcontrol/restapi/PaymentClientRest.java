package com.tcontrol.restapi;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.DTO.PaymentClientDTO;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.PaymentClientService;

@RestController
@RequestMapping("api/t-control/payment-clients/")
public class PaymentClientRest {
	
	@Autowired
	PaymentClientService paymentClientService;
	
	@Autowired
	ResponseJson responseJson;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getClients() {
		return responseJson.messageOK("Lista de Pagos de Clientes Efectuados",paymentClientService.getPaymentAllClients());
	}

	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getClientById(@PathVariable Long id) {
		return responseJson.messageOK("Datos del Pago efectuado",paymentClientService.getPaymentClientById(id));
	}
	
	@GetMapping("/client/{client_id}")
	public ResponseEntity<Map<String, Object>> getPaymentClientByClient(@PathVariable Long client_id) {
		return responseJson.messageOK("Datos del Pago efectuado",paymentClientService.getPaymentClientByClient(client_id));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> savePaymentClient(@Valid @RequestBody PaymentClientDTO paymentClientDTO) {
		return paymentClientService.savePaymentClient(paymentClientDTO, "Create");
				
	}
}
