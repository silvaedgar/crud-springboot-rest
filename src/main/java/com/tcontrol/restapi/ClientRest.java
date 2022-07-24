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

import com.tcontrol.DTO.ClientDTO;
import com.tcontrol.repository.ClientRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.ClientService;

@RestController
@RequestMapping("api/t-control/clients/")
public class ClientRest {

	@Autowired
	public ClientService clientService;

	@Autowired
	public ClientRepository clientRepository;

	@Autowired
	ResponseJson responseJson;

	@GetMapping("status/{status}")
	public ResponseEntity<Map<String, Object>> getClientsActive(@PathVariable String status) {
		return responseJson.messageOK("Listado de Clientes", clientService.getClientsActiveList(status));
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getClients() {
//		return responseJson.messageOK("Listado de Clientes", SpringVersion.getVersion());		
		
		return responseJson.messageOK("Listado de Clientes", clientRepository.findAllByOrderByNamesDesc());		
//		return responseJson.messageOK("Listado de Clientes", clientService.getAllClients());	
	}

	@GetMapping("sort_by_names")
	public ResponseEntity<Map<String, Object>> getAllClients() {
		return responseJson.messageOK("Listado de Clientes Ordenados", clientService.getClientsOrderByNames());	
	}
	
	@GetMapping("find_by_names/{names}")
	public ResponseEntity<Map<String, Object>> getAllClients(@PathVariable String names) {
		return responseJson.messageOK("Listado de Clientes que contienen " + names, clientService.getClientsByNames(names));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getClient(@PathVariable Long id) {
		return responseJson.messageOK("Datos del Cliente con Id " + id, clientService.getClientById(id));	
	}
	
	@GetMapping("movements/{id}")
	public ResponseEntity<Map<String, Object>> getMovementsClient(@PathVariable Long id) {
		return responseJson.messageOK("Datos del Cliente con Id " + id, clientService.getMovementsByClient(id));		
	}


	@PostMapping()
	public ResponseEntity<Map<String,Object>> saveClient(@Valid @RequestBody ClientDTO clientDTO) {		
		return clientService.saveClient(clientDTO, "Create");
	}

	@PutMapping()
	public ResponseEntity<Map<String, Object>> updateClient( @RequestBody @Valid ClientDTO clientDTO) {
		return clientService.saveClient(clientDTO, "Update");
	}
	
}
