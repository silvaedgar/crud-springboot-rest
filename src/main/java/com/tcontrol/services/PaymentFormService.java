package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.PaymentFormDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.PaymentForm;
import com.tcontrol.repository.PaymentFormRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.interfaces.IPaymentForm;

@Service
public class PaymentFormService implements IPaymentForm {

	@Autowired
	PaymentFormRepository paymentFormRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ResponseJson responseJson;
	
	@Override
	public ResponseEntity<Map<String, Object>> savePaymentForm(PaymentFormDTO paymentFormDTO, String option) {

		PaymentForm paymentForm = new PaymentForm();
		PaymentForm existPaymentForm =  getPaymentFormByPaymentForm(paymentFormDTO.getPaymentForm());
		
		paymentForm.setDescription(paymentFormDTO.getDescription());
		paymentForm.setPaymentForm(paymentFormDTO.getPaymentForm());
		paymentForm.setUpdatedAt(LocalDateTime.now());
		paymentForm.setUser(userService.getUserById(paymentFormDTO.getUserId()));
		switch (option) {
		case "Create":
			if (existPaymentForm != null)
				throw new HandlerExceptions("Forma de Pago ya Existe", HttpStatus.BAD_REQUEST);
			paymentForm.setCreatedAt(LocalDateTime.now());
			paymentForm.setStatus("Activo");
			break;
		default:
			PaymentForm paymentFormCurrentForm = getPaymentFormById(paymentFormDTO.getId());
			if (existPaymentForm.getId() != paymentFormCurrentForm.getId())
				throw new HandlerExceptions("Forma de Pago ya Existe", HttpStatus.BAD_REQUEST);
			paymentForm.setId(paymentFormDTO.getId());
			paymentForm.setStatus(paymentFormCurrentForm.getStatus());			
			break;
		}
		
		try {
			return responseJson.messageOK("Forma de Pago " + paymentForm.getPaymentForm() + (option == "Create" ? " creada" : " actualizada")
					+ " con exito", paymentFormRepository.save(paymentForm));			
		} catch (Exception e) {
			throw new HandlerExceptions("Error Ingresando los Datos o Error Interno del Servidor", HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}
	}

	@Override
	public List<PaymentForm> getAllPaymentForm() {
		// TODO Auto-generated method stub
		return paymentFormRepository.findAll();
	}

	@Override
	public PaymentForm getPaymentFormById(Long id) {
		// TODO Auto-generated method stub
		return paymentFormRepository.findById(id)
				.orElseThrow(() -> new HandlerExceptions("ID de Forma de Pago: " + id + " no existe", HttpStatus.NOT_FOUND));
	}

	public PaymentForm getPaymentFormByPaymentForm(String paymentForm) {
		// TODO Auto-generated method stub
		return paymentFormRepository.findByPaymentForm(paymentForm);
	}

}
