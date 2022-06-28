package com.revature.services;

import com.revature.dao.AccountDao;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;

import java.util.Set;

public class AccountService {

    private AccountDao aDao;

    public AccountService(AccountDao aDao) { this.aDao = aDao; }

    /**
     * Opens a new account for a user
     * @param u The user to attach the account to
     */
    public void openAccount(User u) {
        aDao.createAccount(u);
    }

    /**
     * Changes an account's status
     * @param accountNumber The account number of the account to change the status of
     * @param status The account's new status
     * @return the recently updated account
     */
    public Account changeAccountStatus(long accountNumber, Account.Status status) throws AccountNotFoundException {
        Account a = aDao.getAccountByNumber(accountNumber);
        if (a == null) throw new AccountNotFoundException();
        a.setStatus(status);
        aDao.updateAccount(a);
        return a;
    }

    /**
     * Gets all the accounts from a specific user
     * @param username The username of the account
     * @return A set off all the user's accounts
     */
    public Set<Account> getAccountsByUser(String username) {
        return aDao.getAccountsByUser(username);
    }

    /**
     * Gets all the existing accounts
     * @return A set of all existing accounts
     */
    public Set<Account> getAllAccounts(User u) throws UnauthorizedUserException {
        if (!u.getType().equals(User.Type.MANAGER)) {
            throw new UnauthorizedUserException();
        }
        return aDao.getAllAccounts();
    }

    public Account getAccountByNumber(long accountNumber) throws AccountNotFoundException {
        Account result = aDao.getAccountByNumber(accountNumber);
        if (result == null) throw new AccountNotFoundException();
        return result;
    }

    public Set<Account> getAccountByStatus(String status) {
        Set<Account> result = aDao.getAccountByStatus(status);
        return result;
    }

}
