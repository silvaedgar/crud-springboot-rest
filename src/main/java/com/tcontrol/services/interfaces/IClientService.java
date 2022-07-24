package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.ClientDTO;
import com.tcontrol.models.Client;

public interface IClientService {

	public ResponseEntity<Map<String, Object>> saveClient(ClientDTO clientDTO,String option);

	List<Client> getAllClients();
	Client getClientById(Long id);
	List<Client> getClientsActiveList(String status);
	List<Client> getClientsOrderByNames();
	List<Client> getClientsByNames(String names);
	List<Map<String, Object>> getMovementsByClient(Long id);
	
	
	
}
