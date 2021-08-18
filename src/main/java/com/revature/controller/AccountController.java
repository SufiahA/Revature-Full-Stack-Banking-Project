package com.revature.controller;

import io.javalin.http.Context;

public interface AccountController {
	//Create
	public void insertAccount(Context ctx);
	
	//Read
	public void selectAccountById(Context ctx);
	public void selectAccountsByOwnerId(Context ctx);
	public void selectAccountsByOwner(Context ctx);
	
	//Update 
	public void updateAccountInformation(Context ctx);
	public void depositMoney(Context ctx);
	public void withdrawMoney(Context ctx);
	public void transferMoney(Context ctx);
	
	//Not really needed
//	public boolean depositAccounts(Account acc, int amount);
//	public boolean withdrawAccounts(Account acc, int amount);
	
	//Delete 
	public void deleteAccount(Context ctx);

	
}
