package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface AccountDao {
	/*
	 * Implement CRUD methods
	 */
	
	//Create
	public boolean insertAccount(Account newAccount);
	
	//Read
	public Account selectAccountById(int id);
	public List<Account> selectAccountsByOwnerId(int owner_id);
	public List<Account> selectAccountsByOwner(User user);
	
	//Update 
	public boolean updateAccountInformation(Account acc);
	public boolean updateTwoAccounts(Account acc1, Account acc2);
	
	//Not really needed
//	public boolean depositAccounts(Account acc, int amount);
//	public boolean withdrawAccounts(Account acc, int amount);
	
	//Delete 
	public boolean deleteAccount(Account acc);

	

}
