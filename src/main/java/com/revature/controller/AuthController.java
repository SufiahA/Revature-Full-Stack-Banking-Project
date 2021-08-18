package com.revature.controller;

import io.javalin.http.Context;

public interface AuthController {
	
	public void login(Context ctx);
	
	public void logout(Context ctx);
	
	public void employeeLogin(Context ctx);
	
	public boolean checkUser(Context ctx);

	boolean checkAdmin(Context ctx);

}