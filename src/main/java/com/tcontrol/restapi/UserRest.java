package com.tcontrol.restapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.DTO.UserDTO;
import com.tcontrol.models.User;
import com.tcontrol.services.UserService;


@RestController
@RequestMapping("/api/t-control/users/")
public class UserRest {

	Map<String, Object> map = new HashMap<String, Object>();

	@Autowired
	private UserService userService;
	
	@GetMapping("{id}")
	private ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
		
		User user = userService.getUserById(id);

		map.put("data", user);
		map.put("status", "Ok");
		return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
		
	}
	
	@GetMapping
	private List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping
	private ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO ) {
		ResponseEntity<Map<String, Object>> responseEntity = userService.saveUser(userDTO, "Create");
		return responseEntity;
	}
	
	@PutMapping
	private ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDTO userDTO) {
		ResponseEntity<Map<String, Object>> responseEntity = userService.saveUser(userDTO, "Create");
		return responseEntity;
	}
	
	@PatchMapping
	private ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDTO userDTO) {
		
		Optional<User> user = userService.getEmailAndPassword(userDTO);
		map.put("status", (user.isEmpty()?"Acceso Negado" : "Acceso Autorizado"));
		return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
	}
	

}
