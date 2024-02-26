package com.spring.agregadorinvestimentos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
import com.spring.agregadorinvestimentos.controller.dto.UpdateUserDto;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Captor
	private ArgumentCaptor<User> userArgumentCaptor;

	@Captor
	private ArgumentCaptor<UUID> uuidArgumentCaptor;

	@Nested
	class createUser {
		@Test
		@DisplayName("Should create a user whit success")
		void shouldCreateAUserWithSuccess() { // Cenario positivo

			// Arrange
			var user = new User(UUID.randomUUID(), "username", "email@email.com", "123", Instant.now(), null);
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
			var input = new CreateUserDto("username", "email", "123");

			// Act
			var output = userService.createUser(input); // usuário que será retornado após execução do método

			// Assert
			assertNotNull(output); // Certifica que output não está nulo

			var userCaptured = userArgumentCaptor.getValue(); // atribuindo o User capturado à uma variavel

			// Testando se os campos do objeto input são os mesmos do userCaptured
			assertEquals(input.userName(), userCaptured.getUsername());
			assertEquals(input.email(), userCaptured.getEmail());
			assertEquals(input.password(), userCaptured.getPassword());
		}

		@Test
		@DisplayName("Should throw exception when error occurs")
		void shouldThrowExceptionWhenErrorOccurs() { // Cenario negativo

			// Arrange
			doThrow(new RuntimeException()).when(userRepository).save(any());
			var input = new CreateUserDto("username", "email", "123");

			// Act & Assert
			assertThrows(RuntimeException.class, () -> userService.createUser(input)); // usuário que será retornado
																						// após execução do método

		}
	}

	@Nested
	class getUserById {
		@Test
		@DisplayName("Deve buscar um usuário pelo Id com sucesso quando Optional está presente")
		void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() { // Cenário positivo

			// Arrange
			var user = new User(UUID.randomUUID(), "username", "email@email.com", "123", Instant.now(), null);
			doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

			// Act
			var output = userService.getUserById(user.getUserId().toString());

			// Assert
			assertTrue(output.isPresent()); // Verifica se o user está presente na variável output, vale lembrar que é do tipo Optional
			assertEquals(user.getUserId(), uuidArgumentCaptor.getValue()); // Verifica se o Id do user é igual ao Id capturado no método
																
		}

		@Test
		@DisplayName("Não deve buscar um usuário pelo Id com sucesso quando Optional está vazio")
		void shouldNotGetUserByIdWithSuccessWhenOptionalIsEmpty() { // Cenário n

			// Arrange
			var userId = UUID.randomUUID();
			doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

			// Act
			var output = userService.getUserById(userId.toString());

			// Assert
			assertTrue(output.isEmpty()); // Verifica se o user está vazio na variável output, vale lembrar que é do tipo Optional
			assertEquals(userId, uuidArgumentCaptor.getValue()); // Verifica se o Id do user é igual ao Id capturado no método
		}

	}

	@Nested
	class getAll {

		@Test
		@DisplayName("Deve retornar todos usuários com sucesso")
		void shouldReturnAllUsersWithSuccess() {

			// Arrange
			var user = new User(UUID.randomUUID(), "username", "email", "123", Instant.now(), null);
			var userList = List.of(user);
			doReturn(userList).when(userRepository).findAll();

			// Act
			var output = userService.getAll();

			// Assert
			assertNotNull(output);
			assertEquals(userList.size(), output.size()); // Verifica se o tamanho da lista é a mesma da variavel output e utilizada no método
		}
	}

	@Nested
	class deleteById {

		@Test
		@DisplayName("Deve deletar um usuário com sucesso quando um usuário existir")
		void shouldDeleteUserWithSuccessWhenUserExists() { //Cenario positivo

			// Arrange
			doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
			doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture()); // Para métodos void não é
																						// necessário retornar nada
			var userId = UUID.randomUUID();

			// Act
			userService.deleteById(userId.toString());

			// Assert
			var idList = uuidArgumentCaptor.getAllValues(); // Retorna todos objetos capturados na ordem em que foram
															// chamados
			assertEquals(userId, idList.get(0));
			assertEquals(userId, idList.get(1));
			
			verify(userRepository, times(1)).existsById(idList.get(0));
			verify(userRepository, times(1)).deleteById(idList.get(1));
		}
		
		@Test
		@DisplayName("Não deve deletar um usuário com sucesso quando um usuário não existir")
		void shouldNotDeleteUserWithSuccessWhenNotUserExists() { //Cenario negativo

			// Arrange
			doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());
																			// necessário retornar nada
			var userId = UUID.randomUUID();

			// Act
			userService.deleteById(userId.toString());

			// Assert
			assertEquals(userId, uuidArgumentCaptor.getValue());
			
			verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
			verify(userRepository, times(0)).deleteById(any()); //Verificar que esse método NÃO está sendo chamado
		}
	}

	@Nested
	class updateUserById {
		
		@Test
		@DisplayName("Deve atualizar um usuário quando existir com username e senha preenchidos")
		void shouldUpdateUserByIdWhenUserExistsAndUsernameAndPasswordIsFilled() {
			
			//Arrange
			var updateUserDto = new UpdateUserDto("newUsername", "newPassword");
			
			var user = new User(UUID.randomUUID(), "username", "email", "123", Instant.now(), null);
			doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());		
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
		
			//Act
			userService.updateUserById(user.getUserId().toString(), updateUserDto);
			
			//Assert
			assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
			
			var userCaptured = userArgumentCaptor.getValue();
			
			assertEquals(user.getUsername(), userCaptured.getUsername());
			assertEquals(user.getPassword(), userCaptured.getPassword());
			
			verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
			verify(userRepository, times(1)).save(user);
		}
		
		@Test
		@DisplayName("Não deve atualizar um usuário quando ele não existir")
		void shouldNotUpdateUserByIdWhenUserNotExists() {
			
			//Arrange
			var updateUserDto = new UpdateUserDto("newUsername", "newPassword");
			
			var userId = UUID.randomUUID();
			doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());	
			
			//Act
			userService.updateUserById(userId.toString(), updateUserDto);
			
			//Assert
			assertEquals(userId, uuidArgumentCaptor.getValue());
				
			verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
			verify(userRepository, times(0)).save(any()); //Verifica se o método save não foi chamado, pois o usuário não existe
		}
	}
}
