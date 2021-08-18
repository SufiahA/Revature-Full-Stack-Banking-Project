package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface UserDao {
	/**
	 * Enter CRUD methods to implement
	 */
	
	//Create
	public boolean insertUser(User newUser);
	
	//Read
	public User selectUserByUsername(String username);
	public User selectUserById(int id);
	public List<User> selectUnapprovedUsers();
	public List<Account> selectAccsById(int id);
	public List<Account> selectAccsByUsername(String username);
	public List<Account> selectAccsByUser(User user);
	public List<User> selectApprovedUsers();
	
	//Update 
	public boolean updateUserInformation(User user);
	public boolean updateUserActive(User user);
	public boolean updateUserActiveById(int id);
	
	//Delete 
	public boolean deleteUser(User user);
	public boolean deleteUserById(int id);

	
}
