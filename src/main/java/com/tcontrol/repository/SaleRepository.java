package com.tcontrol.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcontrol.models.Client;
import com.tcontrol.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT s.*, cl.names,c.name as coin_name FROM sales s INNER JOIN clients cl ON s.client_id = cl.id " + 
			"INNER JOIN coins c ON s.coin_id = c.id ", nativeQuery = true)
	List<Map<String, Object>> findSalesWithClient();
	
//	@Query("SELECT s FROM Client c JOIN c.sales s")	
//	List<Map<String, Object>> findSalesWithClient();

//	List<Sale> findByClient(Client client);   Next Query idem this. Here extract all data client and items sale detail and next only sale and coin name
	@Query(value = "SELECT s.*, c.name as coin_name FROM sales s INNER JOIN coins c ON s.coin_id = c.id " +
				"WHERE s.client_id = ?1 ORDER BY sale_date", nativeQuery = true)	
	List<Map<String, Object>> findSalesByClient(Long client_id);
	
	List<Sale> findSalesByClientAndStatus(Client client, String status);
	List<Sale> findByStatusIn(Collection<String> status);
	List<Sale> findByStatusInAndClient(Collection<String> status, Client client);
	
	
	@Modifying
	@Query("update Sale s set s.status = 'Historico' where s.client = :client AND s.status = 'Cancelada' ")
	void updateStatusSale(Client client);


	
//	@Query(value = "SELECT s.*, cl.names,c.name as coin_name, sd.*, p.* FROM sales s INNER JOIN clients cl ON s.client_id = cl.id " 
//			 + " INNER JOIN coins c ON s.coin_id = c.id INNER JOIN sale_details sd ON s.id = ds.sale_id "
//			 + " INNER JOIN products p ON sd.product_id = p.id WHERE s.id = ?", nativeQuery = true)
//	List<Map<String, Object>> findSalesDetails(Long id);
}
