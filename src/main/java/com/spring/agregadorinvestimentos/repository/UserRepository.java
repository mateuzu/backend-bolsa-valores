package com.spring.agregadorinvestimentos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.agregadorinvestimentos.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

}
