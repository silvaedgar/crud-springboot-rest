package com.tcontrol.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcontrol.models.Client;
import com.tcontrol.models.PaymentClient;


public interface PaymentClientRepository extends JpaRepository<PaymentClient, Long> {

	@Modifying
	@Query("UPDATE PaymentClient p SET p.status = 'Historico' WHERE p.client = :client AND p.status = 'Procesado' ")
	void updateStatusPaymentClient(Client client);

	@Query(value = "SELECT pc.*, c.name as coin_name FROM payment_clients pc INNER JOIN coins c ON pc.coin_id = c.id " +
			"WHERE pc.client_id = ?1 ORDER BY payment_date", nativeQuery = true)	
	List<Map<String, Object>> findPaymentClientByClient(Long client_id);
	
	
}
