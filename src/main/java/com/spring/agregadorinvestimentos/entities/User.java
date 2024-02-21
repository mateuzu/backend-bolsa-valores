package com.spring.agregadorinvestimentos.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.spring.agregadorinvestimentos.controller.dto.CreateUserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;
	
	@Column(name = "username") //Nome da coluna no banco de dados, caso nome do atributo será alterado o banco de dados não será impactado
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	
	//Camposs de auditoria para saber quando a entidade foi criada/atualizada
	@CreationTimestamp
	private Instant creationTimestamp;
	
	@UpdateTimestamp
	private Instant updateTimestamp;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(UUID userId, String username, String email, String password, Instant creationTimestamp,
			Instant updateTimestamp) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.creationTimestamp = creationTimestamp;
		this.updateTimestamp = updateTimestamp;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Instant getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Instant updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	
	public static User fromDTO (CreateUserDto createUserDto) {
		User user = new User(UUID.randomUUID(),
				createUserDto.userName(),
				createUserDto.email(), 
				createUserDto.password(), 
				Instant.now(), 
				null);
		return user;
	}
	
}
