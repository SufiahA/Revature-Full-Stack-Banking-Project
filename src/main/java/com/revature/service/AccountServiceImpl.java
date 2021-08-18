package com.revature.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.MainDriver;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountServiceImpl implements AccountService {
	private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class);
	private AccountDao accDao = new AccountDaoImpl();

	@Override
	public boolean insertAccount(Account newAccount) {
		return accDao.insertAccount(newAccount);
	}

	@Override
	public Account selectAccountById(int id) {
		return accDao.selectAccountById(id);
	}

	@Override
	public List<Account> selectAccountsByOwnerId(int owner_id) {
		return accDao.selectAccountsByOwnerId(owner_id);
	}

	@Override
	public List<Account> selectAccountsByOwner(User user) {
		return accDao.selectAccountsByOwner(user);
	}

	@Override
	public boolean updateAccountInformation(Account acc) {
		return accDao.updateAccountInformation(acc);
	}
	
	@Override
	public boolean updateTwoAccounts(Account acc1, Account acc2) {
		return accDao.updateTwoAccounts(acc1, acc2);
	}

	@Override
	public boolean deleteAccount(Account acc) {
		return accDao.deleteAccount(acc);
	}

}
