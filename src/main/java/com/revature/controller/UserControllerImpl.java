package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.service.UserServiceImpl;

import io.javalin.http.Context;

public class UserControllerImpl implements UserController {
	private static final Logger LOGGER = Logger.getLogger(UserControllerImpl.class);
	private UserService userService = UserServiceImpl.getInstance();
	private AuthController authController = new AuthControllerImpl();
	
	
	/*-------------------------------------------------------------------------
	 *  Create Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public void insertUser(Context ctx) {
		LOGGER.debug("Processing new user registration info");
		//Take the data from the form
		String firstName = ctx.formParam("first-name");
		String lastName = ctx.formParam("last-name");
		String username = ctx.formParam("username");
		String password1 = ctx.formParam("password1");
		String password2 = ctx.formParam("password2");
		
		// Check each data value separately to make sure it fits
		// Names length between 1 - 30, and letters only
		// username and password 8 - 30 and alphanumeric only
		// Also check that password 1 and 2 match each other
		boolean valid = true;
		valid = valid && (firstName.length() >= 1) && (firstName.length() <= 30) && firstName.matches("[a-zA-Z]*");
		if(valid) System.out.println("First name: " + valid);
		valid = valid && (lastName.length() >= 1) && (lastName.length() <= 30) && lastName.matches("[a-zA-Z]*");
		if(valid) System.out.println("Last name: " + valid);
		valid = valid && (username.length() >= 8)  && (username.length() <= 30) &&  username.matches("^[a-zA-Z0-9]*$");
		if(valid) System.out.println("User name: " + valid);
		valid = valid && (password1.length() >= 8)  && (password1.length() <= 30) &&  password1.matches("^[a-zA-Z0-9]*$");
		if(valid) System.out.println("Password: " + valid);
		valid = valid && password1.equals(password2);
		if(valid) System.out.println("Password2: " + valid);
		
		//Check if the username exists
		User alreadyExists = userService.selectUserByUsername(username);
		valid = valid && (alreadyExists == null);
		
		//Create User object
		User newUser = null;
		System.out.println("Valid: " + valid);
		if(valid) {
			LOGGER.debug("Creating new user now");
			//public User(String user_name, String user_pass, String first_name, String last_name)
			newUser = new User(username, password1, firstName, lastName);
		}
		
		//Try to create the new user
		if(userService.insertUser(newUser)) {
			//Successfully created user. Head back to login page
			LOGGER.debug("Successfully created user. Redirect to register success");
			ctx.status(201);
			ctx.redirect("register-success.html");
		} else {
			//Failed to create user. Send failed status, and redirect to registration page
			LOGGER.debug("Failed to create user. Redirect to register failed");
			ctx.status(400); //
			ctx.redirect("register-error.html");			
		}
	}

	/*-------------------------------------------------------------------------
	 *  Read Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public void selectUserByUsername(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectUserById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectUnapprovedUsers(Context ctx) {
		if(authController.checkAdmin(ctx)) {
			LOGGER.debug("Retrieving list of unapproved users");
			ctx.status(200);
			ctx.json(userService.selectUnapprovedUsers());
		}else {
			LOGGER.debug("Could not retrieve list of unapproved users");
			ctx.status(405);
		}
	}

	@Override
	public void selectAccsById(Context ctx) {
		LOGGER.debug("Selecting accounts by id#");
		User currUser = userService.getCurrUser();
		
		if(authController.checkUser(ctx)) {
			ctx.status(200);
			ctx.json(userService.selectAccsById(currUser.getUser_id()));
			LOGGER.debug("Returning accounts selected by id#");
		}else {
			LOGGER.debug("Could not select acount by id#");
			ctx.status(405);
		}
	}
	
	@Override
	public void employeeSelectAccsById(Context ctx) {
		LOGGER.debug("Selecting accounts by id#");
		int userID = Integer.parseInt(ctx.formParam("userID"));
		System.out.println("userID:" + userID);
		
		if(authController.checkAdmin(ctx)) {
			ctx.status(200);
			ctx.json(userService.selectAccsById(userID));
			LOGGER.debug("Returning accounts selected by id#");
		}else {
			LOGGER.debug("Could not select acount by id#");
			ctx.status(405);
		}
	}

	@Override
	public void selectAccsByUsername(Context ctx) {
		LOGGER.debug("Selecting accounts by username");
		User currUser = userService.getCurrUser();
		
		if(authController.checkUser(ctx)) {
			ctx.status(200);
			ctx.json(userService.selectAccsByUsername(currUser.getUser_name()));
			LOGGER.debug("Returning accounts by username");
		}else {
			LOGGER.debug("Could not select accounts by username");
			ctx.status(405);
		}
	}

	@Override
	public void selectAccsByUser(Context ctx) {
		LOGGER.debug("Selecting accounts by user");
		User currUser = userService.getCurrUser();
		
		if(authController.checkUser(ctx)) {
			ctx.status(200);
			ctx.json(userService.selectAccsByUser(currUser));
			LOGGER.debug("Returning accounts by user");
		}else {
			LOGGER.debug("Could not select accounts by user");
			ctx.status(405);
		}
	}
	
	@Override
	public void selectApprovedUsers(Context ctx) {
		LOGGER.debug("Selecting all user accounts");
		
		if(authController.checkAdmin(ctx)) {
			ctx.status(200);
			ctx.json(userService.selectApprovedUsers());
			LOGGER.debug("Returning all approved user accounts");
		}else {
			LOGGER.debug("Could not select approved user accounts");
			ctx.status(405);
		}
	}

	/*-------------------------------------------------------------------------
	 *  Update Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public void updateUserInformation(Context ctx) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateUserActive(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserActiveById(Context ctx) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void approveRejectUsers(Context ctx) {
		if(authController.checkAdmin(ctx)) {
			//Grab the accounts to approve
			LOGGER.debug("Retrieving accounts to approve");
			String accountsToApprove = ctx.formParam("accToApprove");
			String[] approvalAccounts = accountsToApprove.split(",");
			List<Integer> cleanApprovalAccounts = new ArrayList<Integer>();
			
			//Clean the text inside the accounts for approval
			for(int i = 0; i < approvalAccounts.length; i++) {
				approvalAccounts[i] = approvalAccounts[i].replaceAll("[\\D]", "");
				if(approvalAccounts[i].length() > 0) {
					cleanApprovalAccounts.add(Integer.parseInt(approvalAccounts[i]));
				}
			}
			
			//Approve as many accounts as possible
			for(int account: cleanApprovalAccounts) {
				userService.updateUserActiveById(account);
			}
			LOGGER.debug("Finished approving accounts");
			
			//Grab the accounts to reject
			LOGGER.debug("Retrieving accounts to reject");
			String accountsToReject = ctx.formParam("accToReject");
			String[] rejectionAccounts = accountsToReject.split(",");
			List<Integer> cleanRejectionAccounts = new ArrayList<Integer>();
			
			//Clean the text inside the accounts for rejection
			for(int i = 0; i < rejectionAccounts.length; i++) {
				rejectionAccounts[i] = rejectionAccounts[i].replaceAll("[\\D]", "");
				if(rejectionAccounts[i].length() > 0) {
					cleanRejectionAccounts.add(Integer.parseInt(rejectionAccounts[i]));
				}
			}
			
			//Reject as many accounts as possible
			for(int account: cleanRejectionAccounts) {
				userService.deleteUserById(account);
			}
			LOGGER.debug("Finished rejecting accounts");
		
			//Send status request that we are done.
			ctx.status(200);
			LOGGER.debug("Redirect to finished processing page");
			ctx.redirect("unapproved-users-success.html");
		}else {
			ctx.status(405);
			LOGGER.debug("User does not have access to approve or reject accounts. Redirecting to employee menu");
			ctx.redirect("view-employee-menu.html");
		}
	}

	/*-------------------------------------------------------------------------
	 *  Delete Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public void deleteUser(Context ctx) {
		// TODO Auto-generated method stub
		
	}
}
