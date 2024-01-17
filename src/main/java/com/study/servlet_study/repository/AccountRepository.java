package com.study.servlet_study.repository;

import java.util.ArrayList;
import java.util.List;
import com.study.servlet_study.entity.Account;

//유일하게 하나여야하고 데이터는 여기로 다 저장되어야한다
public class AccountRepository {
	
	private static AccountRepository instance;
	private List<Account> accoutList;
	
	private AccountRepository() {
		accoutList = new ArrayList<>();
	}
	
	//싱글톤
	public static AccountRepository getInstance() {
		if(instance == null) {
			instance = new AccountRepository();
			
		}
		return instance;
	}
	
	public int saveAccount(Account account) {
		accoutList.add(account);
		return 1;
	}
	
	public Account findAccountByUsername(String username) {
		Account findAccount = null;
		
		for(Account account : accoutList) {
			if(account.getUsername().equals(username)) {
				findAccount = account;
				break;				
			}
		}
		
		return findAccount;
	}
	
	
	
	
}
