package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.UserDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.User;
import com.tcontrol.repository.UserRepository;
import com.tcontrol.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
		// TODO Auto-generated method stub
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new HandlerExceptions("Usuario con ID: " + id + "No existe", HttpStatus.NOT_FOUND));
		// TODO Auto-generated method stub
	}
	
	public Optional<User> getEmailAndPassword(UserDTO userDTO) {
		
		String email = userDTO.getEmail();
//		String password = passwordEncoder.encode(userDTO.getPassword());
		String password = (userDTO.getPassword());
		
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveUser(UserDTO userDTO, String option) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = new User();
		user = modelMapper.map(userDTO, User.class);
//		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setUpdatedAt(LocalDateTime.now());
		map.put("data",  user);
		map.put("status", "Ok");
				
		switch (option) {
			case "Create" :
				user.setCreatedAt(LocalDateTime.now());
		}
		try {
			User userSave = userRepository.save(user);
			map.put("data",  userSave);
			map.put("status", "Ok");
			return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new HandlerExceptions("Error de datos, grabando usuario", HttpStatus.NOT_FOUND);
		}
	}	
}
