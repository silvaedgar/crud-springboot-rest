package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.ClientDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.Client;
import com.tcontrol.models.User;
import com.tcontrol.repository.ClientRepository;
import com.tcontrol.services.interfaces.IClientService;


@Service
public class ClientService implements IClientService {

	@Autowired
	public ClientRepository clientRepository;

	@Autowired
	public UserService userService;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public ResponseEntity<Map<String, Object>> saveClient(ClientDTO clientDTO, String option) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();

		Client client = new Client();
		client = modelMapper.map(clientDTO, Client.class);
		client.setUpdatedAt(LocalDateTime.now());
		User user = userService.getUserById(clientDTO.getUserId());
		client.setUser(user);
		
		switch (option) {
		case "Create":
			client.setCreatedAt(LocalDateTime.now());
			client.setStatus("Activo");
			client.setBalance((float) 0.00);
			break;
		default:
			Client clientCurrent = getClientById(clientDTO.getId());
			client.setId(clientCurrent.getId());
			client.setCreatedAt(clientCurrent.getCreatedAt());
			client.setStatus(clientCurrent.getStatus());
			client.setBalance(clientCurrent.getBalance());
			break;
		}

		try {
			Client clientSave = clientRepository.save(client);
			map.put("Data", clientSave);
			map.put("message", "Cliente " + client.getNames() + ". "
					+ (option == "Create" ? "Creado con exito" : "Actualizado con exito"));
			map.put("status", "Ok");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			throw new HandlerExceptions("Error en Datos de Entrada", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public List<Client> getClientsOrderByNames() {
		// TODO Auto-generated method stub
		return clientRepository.findAll(Sort.by("names").descending()) ;		
	}

	
	@Override
	public List<Client> getAllClients() {
		// TODO Auto-generated method stub		
		return clientRepository.findAll() ;
	}
	
	
	@Override
	public List<Client> getClientsActiveList(String status) {
		// TODO Auto-generated method stub
		return clientRepository.findByStatus(status);
	}
	
	@Override
	public List<Client> getClientsByNames(String names) {
		// TODO Auto-generated method stub
		return clientRepository.findByNamesContaining(names);
	}
	
	@Override
	public Client getClientById(Long id) {
		Client client = clientRepository.findById(id)
					.orElseThrow(() -> new HandlerExceptions("Cliente con ID: " + id + " no existe", HttpStatus.NOT_FOUND));
		return client;
	}
	
	@Override
	public List<Map<String, Object>> getMovementsByClient(Long id) {
		// TODO Auto-generated method stub
		return clientRepository.findMovementsClient(id);					
	}


}
