package com.study.servlet_study.Service;

import com.study.servlet_study.entity.Account;
import com.study.servlet_study.repository.AccountRepository;

public class AccountService {
	
	//1
	private static AccountService instance;
	
	
	private AccountRepository accountRepository;
	
	//2
	private AccountService() {
		//
		accountRepository = AccountRepository.getInstance();
	}
	
	//3 싱글톤 
	public static AccountService getInstance() {
		if(instance == null) {
			instance = new AccountService();
		}
		return instance;
	}

	public int addAccount(Account account) {
		return accountRepository.saveAccount(account);
	}
	
	public Account getAccount(String username) {

		return accountRepository.findAccountByUsername(username);
	}
	
	
	
	
	
}
