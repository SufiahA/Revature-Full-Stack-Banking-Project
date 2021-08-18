package com.revature.controller;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Authentication.User;

import com.revature.models.Account;
import com.revature.service.AccountService;
import com.revature.service.AccountServiceImpl;
import com.revature.service.UserService;
import com.revature.service.UserServiceImpl;

import io.javalin.http.Context;

public class AccountControllerImpl implements AccountController {
	private static final Logger LOGGER = Logger.getLogger(AccountControllerImpl.class);
	private AccountService accService = new AccountServiceImpl();
	private UserService userService = UserServiceImpl.getInstance();
	private AuthController authController = new AuthControllerImpl();

	@Override
	public void insertAccount(Context ctx) {
		LOGGER.debug("Processing new account info");
		//Take the data from the form
		String accountType = ctx.formParam("accountType");
		double amount = Double.parseDouble(ctx.formParam("amount"));
		
		// Check each data value separately to make sure it fits
		boolean valid = true;
		valid = valid && (accountType.length() > 0) && (accountType.length() <= 30) && accountType.matches("[a-zA-Z]*");
		valid = valid && (amount >= 0.01) && (amount <= Double.MAX_VALUE);
		
		//Create Account object
		Account newAccount = null;
		System.out.println("Valid: " + valid);
		if(valid) {
			LOGGER.debug("Creating new account now");
			//public User(String user_name, String user_pass, String first_name, String last_name)
			newAccount = new Account(userService.getCurrUser().getUser_id(), accountType, amount);
		}
		
		//Try to create the new user
		if(accService.insertAccount(newAccount)) {
			//Successfully created user. Head back to login page
			LOGGER.debug("Successfully created new account. Redirect to account open success");
			ctx.status(201);
			ctx.redirect("open-account-success.html");
		} else {
			//Failed to create user. Send failed status, and redirect to registration page
			LOGGER.debug("Failed to create user. Redirect to account open failed");
			ctx.status(400); //
			ctx.redirect("open-account-error.html");			
		}
		
	}

	@Override
	public void selectAccountById(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAccountsByOwnerId(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAccountsByOwner(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccountInformation(Context ctx) {
		//TODO
	}
	
	@Override
	public void depositMoney(Context ctx) {
		//Get the values from the form
		double depositAmount = Double.parseDouble(ctx.formParam("amount"));
		int accId = Integer.parseInt(ctx.formParam("account"));
		
		//Check if the deposit amount is valid
		boolean valid = true;
		valid = valid && (depositAmount > 0) && (depositAmount < Double.MAX_VALUE);
		
		//Check if the depositing account exists
		Account selectedAccount = accService.selectAccountById(accId);
		valid = valid && (selectedAccount != null);
		
		//If all valid, then set money to be deposited into account.
		if(valid) {
			selectedAccount.setAcc_amount(selectedAccount.getAcc_amount() + depositAmount);
		}
		
		// Update the account with the new money added
		if(valid && accService.updateAccountInformation(selectedAccount)) {
			//Successfully deposited money. Show deposit success screen
			ctx.status(200);
			ctx.redirect("deposit-success.html");
		} else {
			//Failed to deposit money. Send failed status, and redirect to 
			//deposit failed screen
			ctx.status(400); //
			ctx.redirect("deposit-error.html");			
		}
	}
	
	@Override
	public void withdrawMoney(Context ctx) {
		//Get the values from the form
		double withdrawAmount = Double.parseDouble(ctx.formParam("amount"));
		System.out.println("Withdraw amount: " + withdrawAmount);
		int accId = Integer.parseInt(ctx.formParam("account"));
		System.out.println("Account id: " + accId);
		
		//Check if the depositing account exists
		boolean valid = true;
		Account selectedAccount = accService.selectAccountById(accId);
		valid = valid && (selectedAccount != null);
		System.out.println("selected account is: " + valid);
		
		//Check if the withdraw amount is valid
		valid = valid && (withdrawAmount > 0) && (withdrawAmount < Double.MAX_VALUE);
		valid = valid && (withdrawAmount <= selectedAccount.getAcc_amount());

		//If all valid, then set money to be withdrawn from account.
		if(valid) {
			selectedAccount.setAcc_amount(selectedAccount.getAcc_amount() - withdrawAmount);
		}
		
		// Update the account with the new account balance
		if(valid && accService.updateAccountInformation(selectedAccount)) {
			//Successfully withdrawn money. Show withdraw success screen.
			ctx.status(200);
			ctx.redirect("withdraw-success.html");
		} else {
			//Failed to withdraw money. Send failed status, and redirect to 
			//withdraw failed screen.
			ctx.status(400); //
			ctx.redirect("withdraw-error.html");			
		}
	}
	
	@Override
	public void transferMoney(Context ctx) {
		//Get the values from the form
		double transferAmount = Double.parseDouble(ctx.formParam("amount"));
		System.out.println("Transfer amount: " + transferAmount);
		int fromAccId = Integer.parseInt(ctx.formParam("accountFrom"));
		System.out.println("From Account id: " + fromAccId);
		int toAccId = Integer.parseInt(ctx.formParam("accountTo"));
		System.out.println("To Account id: " + toAccId);
		
		boolean valid = true;
		//Check that the two accounts are not the same
		valid = valid && (fromAccId != toAccId);
		
		//Check that transfer from account exists, and has enough funds
		Account transferFrom = accService.selectAccountById(fromAccId);
		valid = valid && (transferFrom != null);
		if(valid) //Do it this way because we do not want a null pointer exception
			valid = valid && (transferAmount <= transferFrom.getAcc_amount());
		
		//Check that the transfer to account exists
		Account transferTo = accService.selectAccountById(toAccId);
		valid = valid && (transferTo != null);
		
		//If everything is still valid, then switch the amount from both accounts
		if(valid) {
			transferFrom.setAcc_amount(transferFrom.getAcc_amount() - transferAmount);
			transferTo.setAcc_amount(transferTo.getAcc_amount() + transferAmount);
		}
		
		//if it is still valid then commense the transfer!
		if(valid && accService.updateTwoAccounts(transferFrom, transferTo)) {
			ctx.status(200);
			ctx.redirect("transfer-success.html");
		} else {
			ctx.status(400);
			ctx.redirect("transfer-error.html");
		}
	}

	@Override
	public void deleteAccount(Context ctx) {
		// TODO Auto-generated method stub
		
	}

}
