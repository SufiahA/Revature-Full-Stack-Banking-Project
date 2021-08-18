package com.revature.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.revature.controller.AuthController;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.models.User;

public class AuthServiceImpl implements AuthService {
	private static final Logger LOGGER = Logger.getLogger(AuthServiceImpl.class);
	//TODO fix warning to change salt to somehow be static
	private static byte[] salt = new SecureRandom().getSeed(16);
	
	private UserService userService = UserServiceImpl.getInstance();
//	private UserDao userDao = new UserDaoImpl();
	
	private static Map<String, String> tokenRepo = new HashMap<>();
	
	@Override
	public boolean authenticateUser(String username, String password) {
		LOGGER.debug("Authenticating user with username: " + username);
		//Find if we have a user with that username
		User currUser = userService.selectUserByUsername(username);
		if(currUser != null && currUser.getUser_pass().equals(password)) {
			//That user exists, and their password matches too!
			LOGGER.debug("User authenticated");
			userService.setCurrUser(currUser);
			return true;
		} else {
			//A user with that username does not exist
			LOGGER.debug("Could not authenticate user");
			return false;
		}
	}

	@Override
	public String createToken(String username) {
		LOGGER.debug("Creating and storing token for the user");
		String token = simpleHash(username);
		tokenRepo.put(token, username);
		
		return token;
	}
	

	@Override
	public boolean validateToken(String token) {
		LOGGER.debug("Validating user token");
		return tokenRepo.containsKey(token);
	}
	
	public void logout() {
		LOGGER.debug("Logging out the user");
		userService.setCurrUser(null);
	}
	
	/*-------------------------------------------------------------------------
	 * Private method that adds a salt to the username and returns it.
	 *------------------------------------------------------------------------- 
	 */
	private String simpleHash(String username) {
		//Do not add logger to this because this is a private method!
		String hash = null;
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(username.getBytes());
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(0));
			}
			
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}
}