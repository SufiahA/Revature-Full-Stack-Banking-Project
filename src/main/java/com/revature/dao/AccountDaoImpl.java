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
import com.revature.util.ConnectionFactory;

public class AccountDaoImpl implements AccountDao {
	private static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class);
	
	@Override
	public boolean insertAccount(Account newAccount) {
		LOGGER.debug("Creating a new acc and inserting into db");
		boolean result = false;
		//Create a SQL query
		String sql = "INSERT INTO accounts(acc_owner, acc_type, acc_amount)"
				+ " VALUES (?, ?, ?);";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert acc into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, newAccount.getAcc_owner());
			ps.setString(2, newAccount.getAcc_type());
			ps.setDouble(3, newAccount.getAcc_amount());
			
			//Attempt to execute query into db
			ps.execute();
			result = true;
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully created new acc!
		LOGGER.debug("Successfully created new Account in db");
		return result;
	}

	@Override
	public Account selectAccountById(int id) {
		LOGGER.debug("Selecting acc by id");
		Account result = null;
		//Create a SQL query
		String sql = "SELECT * FROM accounts WHERE acc_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert acc into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Attempt to execute query into db
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				result = new Account(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDouble(4)
					);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully retrieved the acc!
		LOGGER.debug("Successfully retrieved Account #" + id);
		return result;
	}

	@Override
	public List<Account> selectAccountsByOwnerId(int owner_id) {
		LOGGER.debug("Retrieving list of accs from db belonging to user");
		List<Account> result = new ArrayList<Account>();
		//Create a SQL query
		String sql = "SELECT * FROM accounts WHERE acc_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert acc into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, owner_id);
			
			//Attempt to execute query into db
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				result.add(new Account(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDouble(4)
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully retrieved many accs related to the account!
		LOGGER.debug("Successfully retrieved all Accounts belonging to user #" + owner_id);
		return result;
	}

	@Override
	public List<Account> selectAccountsByOwner(User user) {
		LOGGER.debug("Retrieving all accs from user #" + user.getUser_id());
		List<Account> result = new ArrayList<Account>();
		//Create a SQL query
		String sql = "SELECT * FROM accounts WHERE acc_owner = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to insert acc into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			
			//Attempt to execute query into db
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				result.add(new Account(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDouble(4)
					));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		//Successfully retrieved many accs related to the account!
		LOGGER.debug("Successfully retrieved all accs relating to user #" + user.getUser_id());
		return result;
	}

	@Override
	public boolean updateAccountInformation(Account acc) {	
		boolean result = false;
		LOGGER.debug("Updating accs for acc #" + acc.getAcc_id());
		//Create a SQL query
		String sql = "UPDATE accounts SET acc_type = ?, acc_amount = ? WHERE acc_id = ?;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, acc.getAcc_type());
			ps.setDouble(2, acc.getAcc_amount());
			ps.setInt(3, acc.getAcc_id());
			
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
		LOGGER.debug("Successfully updated accs #" + acc.getAcc_id());
		return result;
	}
	
	@Override
	public boolean updateTwoAccounts(Account acc1, Account acc2) {
		boolean result = false;
		LOGGER.debug("Updating two accounts for a transfer");
		//Create a SQL query
		String sql = "BEGIN; UPDATE accounts SET acc_amount = ? WHERE acc_id = ?;"
				+ "UPDATE accounts SET acc_amount = ? WHERE acc_id = ?; COMMIT;";
		
		//Create a connection to the db
		try(Connection conn = ConnectionFactory.getConnection()) {
			//Prepare statement object to user into the db
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, acc1.getAcc_amount());
			ps.setInt(2, acc1.getAcc_id());
			ps.setDouble(3, acc2.getAcc_amount());
			ps.setInt(4, acc2.getAcc_id());
			
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
		LOGGER.debug("Successfully updated both accounts and did the transfer");
		return result;
	}

	@Override
	public boolean deleteAccount(Account acc) {
		LOGGER.debug("Deleting acc #" + acc.getAcc_id() + " from the db");
		boolean result = false;
		// Make the sql query to delete the acc
		String sql = "DELETE FROM accounts WHERE acc_id = ?;";
		
		//Create a connection to the database
		try(Connection conn = ConnectionFactory.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, acc.getAcc_id());

			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e);
		} catch(Exception e) {
			LOGGER.error("Unexpected Runtime Exception thrown", e);
			e.printStackTrace();
		}
		LOGGER.debug("Successfully deleted Account #" + acc.getAcc_id());
		return result;
	}
}
