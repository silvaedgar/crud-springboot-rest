package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.UserDTO;
import com.tcontrol.models.User;

public interface IUserService {

	public List<User> getAllUsers();
	public User getUserById(Long id);
	public Optional<User> getEmailAndPassword(UserDTO userDTO);
	public ResponseEntity<Map<String, Object>> saveUser(UserDTO userDTO, String option);
		
}
