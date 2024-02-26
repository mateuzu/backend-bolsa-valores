package com.spring.agregadorinvestimentos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.agregadorinvestimentos.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{

}
