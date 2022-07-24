package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.PaymentClientDTO;
import com.tcontrol.models.PaymentClient;

public interface IPaymentClient {
	
	List<PaymentClient> getPaymentAllClients();
	PaymentClient getPaymentClientById(Long id);

	List<Map<String, Object>> getPaymentClientByClient(Long client_id);

	ResponseEntity<Map<String, Object>> savePaymentClient(PaymentClientDTO paymentClientDTO, String option);

}
