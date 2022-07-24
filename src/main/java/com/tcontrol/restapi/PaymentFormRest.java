package com.tcontrol.restapi;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.DTO.PaymentFormDTO;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.PaymentFormService;

@RestController
@RequestMapping("api/t-control/payment_forms/")
public class PaymentFormRest {
	
	@Autowired
	public PaymentFormService paymentFormService;

	@Autowired
	ResponseJson responseJson;

	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getPaymentForms() {
		return responseJson.messageOK("Listado de Formas de Pago", paymentFormService.getAllPaymentForm());	
	}

	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getPaymentForm(@PathVariable Long id) {
		return responseJson.messageOK("Datos de la Forma de Pago con Id " + id, paymentFormService.getPaymentFormById(id));
	}
		
	@GetMapping("payment_form/{paymentForm}")
	public ResponseEntity<Map<String, Object>> getPaymentFormByPaymentForm(@PathVariable String paymentForm) {
		return responseJson.messageOK("Datos de la Forma de Pago " + paymentForm, paymentFormService.getPaymentFormByPaymentForm(paymentForm));
	}


	@PostMapping()
	public ResponseEntity<Map<String,Object>> savePaymentForm(@Valid @RequestBody PaymentFormDTO paymentFormDTO) {		
		return paymentFormService.savePaymentForm(paymentFormDTO, "Create");
	}

	@PutMapping()
	public ResponseEntity<Map<String,Object>> updatePaymentForm(@Valid @RequestBody PaymentFormDTO paymentFormDTO) {		
		return paymentFormService.savePaymentForm(paymentFormDTO, "Update");
	}
}
