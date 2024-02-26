package com.spring.agregadorinvestimentos.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_accounts")
public class Account {

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID accountId;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id") //Coluna com chave estrageira para tabela de usuário
	private User user;
	
	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private BillingAddress billingAddress;
	
	@OneToMany(mappedBy = "account")
	private List<AccountStock> accountStocks;
	
	public Account() {
		// TODO Auto-generated constructor stub
	}
	
	public Account(UUID accountId, String description, User user, BillingAddress billingAddress,
			List<AccountStock> accountStocks) {
		this.accountId = accountId;
		this.description = description;
		this.user = user;
		this.billingAddress = billingAddress;
		this.accountStocks = accountStocks;
	}

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BillingAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	public List<AccountStock> getAccountStocks() {
		return accountStocks;
	}

	public void setAccountStocks(List<AccountStock> accountStocks) {
		this.accountStocks = accountStocks;
	}
	
}
