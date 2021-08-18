package com.revature.controller;

import java.util.List;

import com.revature.models.User;

import io.javalin.http.Context;

public interface UserController {
	/**
	 * Enter CRUD methods to implement
	 */
	
	//Create
	public void insertUser(Context ctx);
	
	//Read
	public void selectUserByUsername(Context ctx);
	public void selectUserById(int id);
	public void selectUnapprovedUsers(Context ctx);
	public void selectAccsById(Context ctx);
	public void selectAccsByUsername(Context ctx);
	public void selectAccsByUser(Context ctx);
	public void employeeSelectAccsById(Context ctx);
	public void selectApprovedUsers(Context ctx);
	
	//Update 
	public void updateUserInformation(Context ctx);
	public void updateUserActive(Context ctx);
	public void updateUserActiveById(Context ctx);
	public void approveRejectUsers(Context ctx);
	
	//Delete 
	public void deleteUser(Context ctx);

	

	

}
