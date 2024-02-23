package com.spring.agregadorinvestimentos.entities;

import com.spring.agregadorinvestimentos.controller.dto.CreateStockDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_stocks")
public class Stock {
	
	@Id
	@Column(name = "stock_id")
	//Não será gerado automaticamente pois o usuário deve digitar o ticker
	private String stockId; //PETR4, MGLU4 = Exemplos de ticker de ação
	
	@Column(name = "description")
	private String description;
	
	
	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public Stock(String stockId, String description) {
		this.stockId = stockId;
		this.description = description;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static Stock fromDto(CreateStockDto createStockDto) {
		Stock stock = new Stock(createStockDto.stockId(), createStockDto.description());
		return stock;
	}
}
