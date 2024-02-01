package com.spring.agregadorinvestimentos.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.agregadorinvestimentos.controller.dto.CreateUserDto;
import com.spring.agregadorinvestimentos.controller.dto.UpdateUserDto;
import com.spring.agregadorinvestimentos.entities.User;
import com.spring.agregadorinvestimentos.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping
	public ResponseEntity<User> createUser (@RequestBody CreateUserDto createUserDto){
		var userId = userService.createUser(createUserDto);
		return ResponseEntity.created(URI.create("v1/users/" + userId.toString())).build(); //Retorna a URI da localização do id do User criado
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById (@PathVariable("userId") String userId){
		var user = userService.getUserById(userId);
		
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping()
	public ResponseEntity<List<User>> getAll(){
		var users = userService.getAll();
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUserById(@PathVariable("userId")String userId, @RequestBody UpdateUserDto updateUserDto){
		var user = userService.updateUserById(userId, updateUserDto);
		if(user != null) {
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable String userId) {
		userService.deleteById(userId);
	}
}
