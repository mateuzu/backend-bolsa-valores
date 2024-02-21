package com.spring.agregadorinvestimentos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.agregadorinvestimentos.controller.dto.CreateUserDto;
import com.spring.agregadorinvestimentos.entities.User;
import com.spring.agregadorinvestimentos.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Captor
	private ArgumentCaptor<User> userArgumentCaptor;
	
	@Nested
	class createUser{
		
		@Test
		@DisplayName("Should create a user whit success")
		void shouldCreateAUserWithSuccess() { //Cenario positivo
			
			//Arrange
			var user = new User(UUID.randomUUID(), "username", "email@email.com", "123", Instant.now(), null);
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
			var input = new CreateUserDto("username", "email", "123");
					
			//Act
			var output = userService.createUser(input); //usuário que será retornado após execução do método
			
			
			//Assert
			assertNotNull(output); //Certifica que output não está nulo
			
			var userCaptured = userArgumentCaptor.getValue(); //atribuindo o User capturado à uma variavel
			
			//Testando se os campos do objeto input são os mesmos do userCaptured
			assertEquals(input.userName(), userCaptured.getUsername());
			assertEquals(input.email(), userCaptured.getEmail());
			assertEquals(input.password(), userCaptured.getPassword());
		}
		
		@Test
		@DisplayName("Should throw exception when error occurs")
		void shouldThrowExceptionWhenErrorOccurs() { //Cenario negativo
		
			//Arrange
			doThrow(new RuntimeException()).when(userRepository).save(any());
			var input = new CreateUserDto("username", "email", "123");
					
			//Act & Assert
			assertThrows(RuntimeException.class, () -> userService.createUser(input)); //usuário que será retornado após execução do método
			
		}
	}
}
