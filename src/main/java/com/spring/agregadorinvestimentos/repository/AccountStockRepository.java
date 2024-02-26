package com.spring.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.agregadorinvestimentos.entities.AccountStock;
import com.spring.agregadorinvestimentos.entities.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId>{

}
