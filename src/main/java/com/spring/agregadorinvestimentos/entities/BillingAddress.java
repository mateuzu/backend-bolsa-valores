package com.spring.agregadorinvestimentos.entities;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_billingaddress")
public class BillingAddress {

	@Id
	@Column(name = "account_id")
	private UUID id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "account_id")
	private Account account;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "number")
	private Integer number;
	
	public BillingAddress() {
		// TODO Auto-generated constructor stub
	}

	public BillingAddress(UUID id, String street, Integer number) {
		this.id = id;
		this.street = street;
		this.number = number;
	}
	
	public BillingAddress(UUID id, Account account, String street, Integer number) {
		this.id = id;
		this.account = account;
		this.street = street;
		this.number = number;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
