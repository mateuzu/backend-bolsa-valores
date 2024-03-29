package com.spring.agregadorinvestimentos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.spring.agregadorinvestimentos.client.BrapiClient;
import com.spring.agregadorinvestimentos.controller.dto.AccountStockResponseDto;
import com.spring.agregadorinvestimentos.controller.dto.AssociateAccountStockDto;
import com.spring.agregadorinvestimentos.entities.AccountStock;
import com.spring.agregadorinvestimentos.entities.AccountStockId;
import com.spring.agregadorinvestimentos.repository.AccountRepository;
import com.spring.agregadorinvestimentos.repository.AccountStockRepository;
import com.spring.agregadorinvestimentos.repository.StockRepository;

@Service
public class AccountService {
	
	@Value("#{environment.TOKEN}")
	private String TOKEN;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private AccountStockRepository accountStockRepository;
	
	@Autowired
	private BrapiClient brapiClient;

	public void associateStock(String accountId, AssociateAccountStockDto dto) {
		var account = accountRepository.findById(UUID.fromString(accountId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //Verifica se account existe
		
		var stock = stockRepository.findById(dto.stockId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //Verifica se stock existe
		
		//DTO -> Entity
		var id = new AccountStockId(account.getAccountId(), stock.getStockId()); //Id composto da entidade que relaciona Account e Stock
		var entity = new AccountStock(id, account, stock, dto.quantity()); //Cria a entiadde associativa entre Account e Stock
		
		accountStockRepository.save(entity);
	}

	public List<AccountStockResponseDto> listStocks(String accountId) {
		var account = accountRepository.findById(UUID.fromString(accountId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return account.getAccountStocks()
				.stream()
					.map(accountStock -> new AccountStockResponseDto(
							accountStock.getStock().getStockId(), 
							accountStock.getQuantity(),
							getTotal(accountStock.getQuantity(), accountStock.getStock().getStockId())))
				.toList();
	}

	private Double getTotal(Integer quantity, String stockId) {
		var response = brapiClient.getQuote(TOKEN, stockId); //lista de resultados com diversas informações
		var price = response.results().get(0).regularMarketPrice(); //Captura o primeiro item da lista de resultados
		
		return quantity * price;
	}
}
