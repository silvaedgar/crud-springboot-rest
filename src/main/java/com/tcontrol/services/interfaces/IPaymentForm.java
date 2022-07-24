package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.PaymentFormDTO;
import com.tcontrol.models.PaymentForm;

public interface IPaymentForm {
	
	public ResponseEntity<Map<String, Object>> savePaymentForm(PaymentFormDTO paymentFormDTO, String option);
	public PaymentForm getPaymentFormByPaymentForm(String paymentForm);

	public List<PaymentForm> getAllPaymentForm();
	public PaymentForm getPaymentFormById(Long id);
}
