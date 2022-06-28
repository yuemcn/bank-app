package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;

import java.util.Set;

public interface AccountDao {

    public Set<Account> getAllAccounts();
    public Set<Account> getAccountsByUser(String username);
    public Set<Account> getAccountsLessBalanceThan(double balance);
    public Account getAccountByNumber(long accountNumber);
    public Set<Account> getAccountByStatus(String status);

    public void createAccount(User user);
    public void updateAccount(Account account);
    public void deleteAccountByNumber(long accountNumber);

}
