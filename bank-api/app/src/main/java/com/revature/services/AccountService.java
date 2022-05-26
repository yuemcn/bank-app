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
    public Account openAccount(User u) {
        Account a = new Account(u);
        a.setAccountNumber(generateAccountNumber());
        aDao.createAccount(a);
        LoggingUtil.logger.info("Created Account #" + a.getAccountNumber());
        return a;
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
        LoggingUtil.logger.info("Updated Account #" + a.getAccountNumber());
        return a;
    }

    /**
     * Gets all the accounts from a specific user
     * @param username The username of the account
     * @return A set off all the user's accounts
     */
    public Set<Account> getAccountsByUser(String username) {
        LoggingUtil.logger.info("Retrieved accounts from user " + username);
        return aDao.getAccountsByUser(username);
    }

    /**
     * Gets all the existing accounts
     * @return A set of all existing accounts
     */
    public Set<Account> getAllAccounts(User u) throws UnauthorizedUserException {
        if (!u.getType().equals(User.Type.MANAGER)) {
            LoggingUtil.logger.info("Could not get all accounts because user is not a manager");
            throw new UnauthorizedUserException();
        }
        LoggingUtil.logger.info("Retrieved all accounts");
        return aDao.getAllAccounts();
    }

    public Account getAccountByNumber(long accountNumber) throws AccountNotFoundException {
        Account result = aDao.getAccountByNumber(accountNumber);
        if (result == null) throw new AccountNotFoundException();
        return result;
    }

    /**
     * Generates a random account number that does not currently exist
     * @return a new account number
     */
    public long generateAccountNumber() {
        long result;
        do {
            result = 1111111111l + (long) (Math.random() * (9999999999l - 1111111111l));
        } while (aDao.getAccountByNumber(result) != null);
        return result;
    }

}
