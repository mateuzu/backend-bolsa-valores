package com.spring.agregadorinvestimentos.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.agregadorinvestimentos.controller.dto.CreateUserDto;
import com.spring.agregadorinvestimentos.controller.dto.UpdateUserDto;
import com.spring.agregadorinvestimentos.entities.User;
import com.spring.agregadorinvestimentos.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
		
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UUID createUser (CreateUserDto createUserDto) {
		//DTO -> Entity
		var entity = User.fromDTO(createUserDto);
		
		var userSaved = userRepository.save(entity);
		return userSaved.getUserId();
	}
	
	public Optional<User> getUserById(String userId) {
		var id = UUID.fromString(userId);
		return userRepository.findById(id);
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public User updateUserById(String userId, UpdateUserDto updateUserDto) {
		var id = UUID.fromString(userId);
		var userEntity = userRepository.findById(id);
		
		if(userEntity.isPresent()) {
			var user = userEntity.get();
			
			if(updateUserDto.username() != null) {
				user.setUsername(updateUserDto.username());
			}
			
			if(updateUserDto.password() != null) {
				user.setPassword(updateUserDto.password());
			}
			
			userRepository.save(user);
			return user;
		}
		return null;
	}
	
	public void deleteById(String userId) {
		var id = UUID.fromString(userId);
		var userExists = userRepository.existsById(id);
		if(userExists) {
			userRepository.deleteById(id);
		}
	}
}
