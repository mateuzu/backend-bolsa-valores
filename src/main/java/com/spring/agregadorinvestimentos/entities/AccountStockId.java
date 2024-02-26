package com.spring.agregadorinvestimentos.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AccountStockId {

	@Column(name = "account_id")
	private UUID accountId; //Id da entidade Account
	
	@Column(name = "stock_id")
	private String stockId; //Id da entidade Stock
	
	public AccountStockId() {
		// TODO Auto-generated constructor stub
	}

	public AccountStockId(UUID accountId, String stockId) {
		this.accountId = accountId;
		this.stockId = stockId;
	}

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
		
}
