package com.revature.dao;

import com.revature.models.Account;

import java.util.Set;

public interface AccountDao {

    public Set<Account> getAllAccounts();
    public Set<Account> getAccountsByUser(String username);
    public Set<Account> getAccountsLessBalanceThan(double balance);
    public Account getAccountByNumber(long accountNumber);

    public void createAccount(Account account);
    public void updateAccount(Account account);
    public void deleteAccountByNumber(long accountNumber);

}
