package com.revature.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;

public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	private static UserServiceImpl instance;
	private UserDao userDao = new UserDaoImpl();
	private static User currUser;
	
	private UserServiceImpl() {
		super();
	}
	
	public static UserServiceImpl getInstance() {
		if(UserServiceImpl.instance == null) {
			instance = new UserServiceImpl();
		}
		return UserServiceImpl.instance;
	}
	
	/*-------------------------------------------------------------------------
	 *  Getter and Setter Methods
	 * ------------------------------------------------------------------------
	 * */
	public User getCurrUser() {
		LOGGER.debug("Get current user");
		return currUser;
	}

	public void setCurrUser(User user) {
		LOGGER.debug("Set current user");
		currUser = user;
	}
	
	/*-------------------------------------------------------------------------
	 *  Create Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public boolean insertUser(User newUser) {
		LOGGER.debug("Inserting new user");
		return userDao.insertUser(newUser);
	}

	/*-------------------------------------------------------------------------
	 *  Read Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public User selectUserByUsername(String username) {
		LOGGER.debug("Selecting user by username");
		return userDao.selectUserByUsername(username);
	}

	@Override
	public User selectUserById(int id) {
		LOGGER.debug("Selecting user by id");
		return userDao.selectUserById(id);
	}

	@Override
	public List<User> selectUnapprovedUsers() {
		LOGGER.debug("Selecting all unapproved users");
		return userDao.selectUnapprovedUsers();
	}

	@Override
	public List<Account> selectAccsById(int id) {
		LOGGER.debug("Select all accounts related to an id");
		return userDao.selectAccsById(id);
	}

	@Override
	public List<Account> selectAccsByUsername(String username) {
		LOGGER.debug("Selecting all accounts related to a username");
		return userDao.selectAccsByUsername(username);
	}

	@Override
	public List<Account> selectAccsByUser(User user) {
		LOGGER.debug("Selecting all accounts related to a user");
		return userDao.selectAccsByUser(user);
	}
	
	@Override
	public List<User> selectApprovedUsers() {
		return userDao.selectApprovedUsers();
	}

	/*-------------------------------------------------------------------------
	 *  Update Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public boolean updateUserInformation(User user) {
		LOGGER.debug("Update user information");
		return userDao.updateUserInformation(user);
	}

	@Override
	public boolean updateUserActive(User user) {
		LOGGER.debug("Has not been implemented: updateUserActive");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserActiveById(int id) {
		LOGGER.debug("Update user approval by id");
		return userDao.updateUserActiveById(id);
	}
	
	/*-------------------------------------------------------------------------
	 *  Delete Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public boolean deleteUser(User user) {
		LOGGER.debug("Delete user");
		return userDao.deleteUser(user);
	}

	@Override
	public boolean deleteUserById(int id) {
		LOGGER.debug("Delete user by id");
		return userDao.deleteUserById(id);
	}
}
