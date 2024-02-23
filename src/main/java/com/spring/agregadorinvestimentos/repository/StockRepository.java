package com.spring.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.agregadorinvestimentos.entities.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String>{

}
