package com.spring.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;

import com.spring.agregadorinvestimentos.controller.dto.CreateStockDto;
import com.spring.agregadorinvestimentos.entities.Stock;
import com.spring.agregadorinvestimentos.repository.StockRepository;

@Service
public class StockService {

	private StockRepository stockRepository;
	
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository; //Injeção de dependencia por construtor
	}

	public void createStock(CreateStockDto createStockDto) {
		
		//DTO -> Entity
		var stock = Stock.fromDto(createStockDto);
		
		stockRepository.save(stock);
	}
}
