package com.tcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcontrol.models.PaymentForm;

public interface PaymentFormRepository extends JpaRepository<PaymentForm, Long> {

	PaymentForm findByPaymentForm(String paymentForm);
	

}
