package com.revature.controller;

import org.apache.log4j.Logger;

import com.revature.MainDriver;
import com.revature.models.User;
import com.revature.service.AuthService;
import com.revature.service.AuthServiceImpl;
import com.revature.service.UserService;
import com.revature.service.UserServiceImpl;

import io.javalin.http.Context;

public class AuthControllerImpl implements AuthController {
	private static final Logger LOGGER = Logger.getLogger(AuthController.class);
	private AuthService authService = new AuthServiceImpl();
	private UserService userService = UserServiceImpl.getInstance();

	@Override
	public void login(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		LOGGER.debug("Customer attempting to log in with username: " + username);
		
		//Check if account with the username and password is correct
		if(authService.authenticateUser(username, password)) {
			//Successfully logged in! Send a status code, create a cookie, and
			//redirect to main account page
			LOGGER.debug("Customer successfully signed in");
			ctx.status(200);
			ctx.cookieStore("user", authService.createToken(username));
			ctx.redirect("view-menu.html");
		} else {
			//Could not find user, or incorrect password and therefore could
			//not log in
			LOGGER.debug("Customer could not sign in. Sent back to login screen.");
			ctx.status(407);
			//clear cookiestore and logout just to be sure
			ctx.clearCookieStore();
			authService.logout();
			ctx.redirect("login.html");
		}
	}

	
	@Override
	public void logout(Context ctx) {
		//Clear cookie store, set status to 200 for successful logout, and 
		//redirect back to main page
		LOGGER.debug("Logging out user");
		ctx.clearCookieStore();
		authService.logout();
		ctx.status(200);
		ctx.redirect("login.html");
	}

	@Override
	public boolean checkUser(Context ctx) {
		// Checks if 
		LOGGER.debug("Check if customer is signed in.");
		return authService.validateToken(ctx.cookieStore("user"));
	}


	@Override
	public void employeeLogin(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		LOGGER.debug("Employee attempting to log in with username: " + username);
		
		//Check if account with the username and password is correct
		boolean valid = authService.authenticateUser(username, password);
		
		//Check if account is an employee account
		User employee = userService.getCurrUser();
		valid = valid && employee.isEmployee();
		
		if(valid) {
			//Successfully logged in! Send a status code, create a cookie, and
			//redirect to main account page
			LOGGER.debug("Employee successfully signed in.");
			ctx.status(200);
			ctx.cookieStore("admin", authService.createToken(username));
			ctx.redirect("view-employee-menu.html");
		} else {
			//Could not find user, or incorrect password and therefore could
			//not log in
			LOGGER.debug("Employee could not sign in. Sent back to employee login screen.");
			ctx.status(407);
			//clear cookiestore and logout just to be sure
			ctx.clearCookieStore();
			authService.logout();
			ctx.redirect("employee-login.html");
		}
		
	}
	
	@Override
	public boolean checkAdmin(Context ctx) {
		// Checks if the current user is an employee, and is signed in
		LOGGER.debug("Check if employee is signed in.");
		return authService.validateToken(ctx.cookieStore("admin"));
	}

}