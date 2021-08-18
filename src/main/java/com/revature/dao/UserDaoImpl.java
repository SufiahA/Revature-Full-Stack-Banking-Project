package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.service.UserServiceImpl;
import com.revature.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

	public UserDaoImpl() {
		super();
	}
	
	/*-------------------------------------------------------------------------
	 *  Create Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public boolean insertUser(User newUser) {
		LOGGER.info("Starting to insert new user into db");
		// To avoid null pointer exceptions
		if(newUser == null) {
			LOGGER.error("Cannot insert a null user into db");
			return false;
		}
		
		boolean result = false;
		//Create a SQL query
		String sql = "INSERT INTO users(user_name, user_pass, first_name,"
				+ " last_name, active, employee) VALUES ("
				+ "?, ?, ?, ?, FALSE, FALSE);";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			LOGGER.info("Created the db connection");
			//Prepare statement object to insert new user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			LOGGER.info("Preparing SQL statement");
			ps.setString(1, newUser.getUser_name());
			ps.setString(2, newUser.getUser_pass());
			ps.setString(3, newUser.getFirst_name());
			ps.setString(4, newUser.getLast_name());
			
			//Attempt to execute query into db
			ps.execute();
			result = true;
			LOGGER.info("Successfully executed and added new user");
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(NullPointerException e) {
			LOGGER.error("NullPointerException thrown!", e);
			System.out.println("User value is null");
			e.printStackTrace();
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully created new user that needs to be approved.
		LOGGER.info("Successfully created new user that needs to be approved now.");
		return result;
	}

	/*-------------------------------------------------------------------------
	 *  Read Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public User selectUserByUsername(String username) {
		LOGGER.info("Starting to select user by username");
		// To avoid null pointer exceptions
		if(username == null || username.length() <= 0) {
			LOGGER.error("Username is null, or less than length 0. Aborting process");
			return null;
		}
		
		User user = null;
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE user_name = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			LOGGER.info("Preparing SQL statement");
			ps.setString(1, username);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			LOGGER.info("Received query result from db");
			//Convert the returned fruit into an object
			if(rs.next()) {
				user = new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully retrieved user based on username.
		LOGGER.info("Successfully retrieved user based on user_name:" + username);
		return user;
	}

	@Override
	public User selectUserById(int id) {
		LOGGER.info("Starting to select user based on user id");
		User user = null;
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE user_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fruit into an object
			if(rs.next()) {
			user = new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully retrieved user based on user_id.
		LOGGER.info("Successfully retrieved user based on user_id#" + id);
		return user;
	}

	@Override
	public List<User> selectUnapprovedUsers() {
		LOGGER.info("Starting to select all unapproved users from db");
		List<User> unapprovedList = new ArrayList<User>();
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE active = FALSE;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned users into an object
			while(rs.next()) {
				unapprovedList.add(new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully return list of unapproved users.
		LOGGER.info("Successfully returned List of all unapproved user accounts");
		return unapprovedList;
	}
	
	@Override
	public List<Account> selectAccsById(int id) {
		LOGGER.info("Starting to select all the user's funds");
		List<Account> accs = new ArrayList<Account>();
		String sql = "SELECT * FROM accounts WHERE acc_owner = ? ORDER BY acc_id ASC;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fund accounts into an object
			while(rs.next()) {
			accs.add(new Account(
					rs.getInt(1),	//serial
					rs.getInt(2),//fund_owner
					rs.getString(3),//fund_type
					rs.getDouble(4) //fund_amount
					));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully returned all funds related to one account.
		LOGGER.info("Successfully returned all funds related to the account");
		return accs;
	}

	@Override
	public List<Account> selectAccsByUsername(String username) {
		LOGGER.info("Selecting all funds associated to the user through username");
		List<Account> accs = new ArrayList<Account>();
		User user = selectUserByUsername(username);
		
		String sql = "SELECT * FROM accounts WHERE acc_owner = ? ORDER BY acc_id ASC;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fund accounts into an object
			while(rs.next()) {
			accs.add(new Account(
					rs.getInt(1),	//serial
					rs.getInt(2),//fund_owner
					rs.getString(3),//fund_type
					rs.getDouble(4) //fund_amount
					));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully returned all funds related to one account.
		LOGGER.info("Successfully returned all funds related to the account");
		return accs;
	}

	@Override
	public List<Account> selectAccsByUser(User user) {
		LOGGER.info("Starting to select all funds related to user from db");
		List<Account> accs = new ArrayList<Account>();
		
		// To avoid null pointer exceptions
		if(user == null) {
			// Return empty list
			return accs;
		}
		
		String sql = "SELECT * FROM accounts WHERE acc_owner = ? ORDER BY acc_id ASC;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned fund accounts into an object
			while(rs.next()) {
			accs.add(new Account(
					rs.getInt(1),	//serial
					rs.getInt(2),//fund_owner
					rs.getString(3),//fund_type
					rs.getDouble(4) //fund_amount
					));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully returned all funds related to one account.
		LOGGER.info("Successfully returned all funds related to the account");
		return accs;
	}
	
	@Override
	public List<User> selectApprovedUsers() {
		LOGGER.info("Starting to select all approved users from db");
		List<User> approvedList = new ArrayList<User>();
		//Create a SQL query
		String sql = "SELECT * FROM users WHERE active = TRUE;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to retrieve user from db
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//Attempt to execute query from db
			ResultSet rs = ps.executeQuery();
			
			//Convert the returned users into an object
			while(rs.next()) {
				approvedList.add(new User(
					rs.getInt(1),	//serial
					rs.getString(2),//user_name
					rs.getString(3),//user_pass
					rs.getString(4), //first_name
					rs.getString(5), //last_name
					rs.getBoolean(6), //active
					rs.getBoolean(7) //employee
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully return list of unapproved users.
		LOGGER.info("Successfully returned List of all approved user accounts");
		return approvedList;
	}

	/*-------------------------------------------------------------------------
	 *  Update Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public boolean updateUserInformation(User user) {
		// To avoid null pointer exceptions
		if(user == null) {
			return false;
		}
		
		LOGGER.info("Starting to update user information in db");
		boolean result = false;
		
		//Create a SQL query
		String sql = "UPDATE users SET user_name = ?, user_pass = ?, first_name = ?,"
				+ " last_name = ?, active = ?, employee = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_name());
			ps.setString(2, user.getUser_pass());
			ps.setString(3, user.getFirst_name());
			ps.setString(4, user.getLast_name());
			ps.setBoolean(5, user.isActive());
			ps.setBoolean(6, user.isEmployee());
			
			//Attempt to execute query into db
			ps.executeUpdate();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		LOGGER.info("Successfully updated the user account");
		return result;
	}
	
	@Override
	public boolean updateUserActive(User user) {
		// To avoid null pointer exceptions
		if(user == null) {
			return false;
		}
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserActiveById(int id) {
		LOGGER.info("Starting to update user information in db");
		boolean result = false;
		
		//Create a SQL query
		String sql = "UPDATE users SET active = TRUE WHERE user_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query into db
			ps.executeUpdate();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		LOGGER.info("Successfully updated the user account");
		return result;
	}

	/*-------------------------------------------------------------------------
	 *  Delete Methods
	 * ------------------------------------------------------------------------
	 * */
	@Override
	public boolean deleteUser(User user) {
		// To avoid null pointer exceptions
		if(user == null) {
			return false;
		}
		
		LOGGER.info("Starting to delete user from db");
		boolean result = false;
		// Make the sql query to delete the user
		String sql = "DELETE FROM users WHERE user_id = ?;";
		
		//Create a connection to the database
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());

			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		LOGGER.info("Successfully deleted the user");
		return result;
	}

	@Override
	public boolean deleteUserById(int id) {
		LOGGER.info("Starting to delete user from db");
		boolean result = false;
		// Make the sql query to delete the user
		String sql = "DELETE FROM users WHERE user_id = ?;";
		
		//Create a connection to the database
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		} 
		LOGGER.info("Successfully deleted the user");
		return result;
	}
}
