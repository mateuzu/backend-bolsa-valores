package com.spring.agregadorinvestimentos.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.spring.agregadorinvestimentos.controller.dto.AccountResponseDto;
import com.spring.agregadorinvestimentos.controller.dto.CreateAccountDto;
import com.spring.agregadorinvestimentos.controller.dto.CreateUserDto;
import com.spring.agregadorinvestimentos.controller.dto.UpdateUserDto;
import com.spring.agregadorinvestimentos.entities.Account;
import com.spring.agregadorinvestimentos.entities.BillingAddress;
import com.spring.agregadorinvestimentos.entities.User;
import com.spring.agregadorinvestimentos.repository.AccountRepository;
import com.spring.agregadorinvestimentos.repository.BillingAddressRepository;
import com.spring.agregadorinvestimentos.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BillingAddressRepository billingAddressRepository;
		
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

	public void createAccount(String userId, CreateAccountDto createAccountDto) {
		var user = userRepository.findById(UUID.fromString(userId))
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		//DTO -> ENTITY
		var account = new Account(UUID.randomUUID(),createAccountDto.description(), user, null, null);
		
		var accountCreated = accountRepository.save(account);
		
		var billingAddress = new BillingAddress(
				accountCreated.getAccountId(), 
				account, 
				createAccountDto.street(), 
				createAccountDto.number()
				);
		
		billingAddressRepository.save(billingAddress);
	}

	public List<AccountResponseDto> listAccounts(String userId) {
		var user = userRepository.findById(UUID.fromString(userId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return user.getAccounts()
		.stream()
			.map(userAccount-> 
				new AccountResponseDto(userAccount.getAccountId().toString(), userAccount.getDescription()))
			.toList();
	}
}
