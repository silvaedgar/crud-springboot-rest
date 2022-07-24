package com.tcontrol.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcontrol.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	List<Client> findByNamesContaining(String names);
	List<Client> findByStatus(String status);
	List<Client> findAllByOrderByNamesDesc();	
	List<Client> findAllByOrderByNamesAsc();	
	
	String query = "SELECT sale_date AS date, s.id, s.created_at AS created, mount, rate_exchange, balance, cl.id AS client_id, "
				+ " cl.names, cl.count_in_bs, 'Compras' AS type, c.symbol, c.id AS coin_id, (mount * rate_exchange) AS mount_balance_mult, "
				+ " (mount / rate_exchange) AS mount_balance_div FROM clients cl INNER JOIN sales s  ON s.client_id = cl.id "
				+ " INNER JOIN coins c ON s.coin_id = c.id WHERE s.client_id = :id AND s.status != 'Historico' AND s.status != 'Anulada'"
				+ " UNION "
				+ " SELECT payment_date AS date, pc.id, pc.created_at AS created, mount, rate_exchange, balance, cl.id AS client_id, "
				+ " cl.names, cl.count_in_bs, 'Pagos' AS type, c.symbol, c.id AS coin_id, (mount * rate_exchange) AS mount_balance_mult, "
				+ " (mount / rate_exchange) AS mount_balance_div FROM clients cl INNER JOIN payment_clients pc  ON pc.client_id = cl.id "
				+ " INNER JOIN coins c ON pc.coin_id = c.id WHERE pc.client_id = :id AND pc.status != 'Historico' AND pc.status != 'Anulado'"
				+ " ORDER BY 1";
	@Query(value = query, nativeQuery = true)
	List<Map<String, Object>> findMovementsClient(@Param(value = "id")  Long id);


//	@Query("select c from Client c where c.status = :param")
//	List<Client> findByStatus(@Param (value="param") String status);

}
