package com.spring.agregadorinvestimentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.agregadorinvestimentos.controller.dto.AccountStockResponseDto;
import com.spring.agregadorinvestimentos.controller.dto.AssociateAccountStockDto;
import com.spring.agregadorinvestimentos.service.AccountService;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/{accountId}/stocks")
	public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId, @RequestBody AssociateAccountStockDto dto ){
		accountService.associateStock(accountId, dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{accountId}/stocks")
	public ResponseEntity<List<AccountStockResponseDto>> listStocks(@PathVariable("accountId") String accountId){
		var stocks = accountService.listStocks(accountId);
		return ResponseEntity.ok(stocks);
	}
}
