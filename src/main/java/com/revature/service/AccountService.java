package com.revature.service;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface AccountService {
	//Create
	public boolean insertAccount(Account newAccount);
	
	//Read
	public Account selectAccountById(int id);
	public List<Account> selectAccountsByOwnerId(int owner_id);
	public List<Account> selectAccountsByOwner(User user);
	
	//Update 
	public boolean updateAccountInformation(Account acc);
	public boolean updateTwoAccounts(Account acc1, Account acc2);
	
	//Delete 
	public boolean deleteAccount(Account acc);

	

}
