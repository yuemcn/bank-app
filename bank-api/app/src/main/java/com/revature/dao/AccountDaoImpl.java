package com.revature.dao;

import com.revature.models.Account;
import com.revature.utils.DAOUtilities;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoImpl implements AccountDao {

    Connection connection = null;
    PreparedStatement stmt = null;


    @Override
    public Set<Account> getAllAccounts() {
        Set<Account> accounts = new HashSet<>();
        String sql = "select * from accounts;";

        try {
            UserDao uDao = new UserDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Account a = new Account();
                a.setAccountNumber(rs.getLong(1));
                a.setUser(uDao.getUserByUsername(rs.getString(2)));
                a.setBalance(rs.getLong(3));
                String str = rs.getString(4);
                if (str.equalsIgnoreCase("ACTIVE")) a.setStatus(Account.Status.ACTIVE);
                else if (str.equalsIgnoreCase("INACTIVE")) a.setStatus(Account.Status.INACTIVE);
                else a.setStatus(Account.Status.DEACTIVATED);

                accounts.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Set<Account> getAccountsByUser(String username) {
        Set<Account> accounts = new HashSet<>();
        String sql = "select * from accounts where account_owner = '" + username + "';";

        try {
            UserDao uDao = new UserDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Account a = new Account();
                a.setAccountNumber(rs.getLong(1));
                a.setUser(uDao.getUserByUsername(rs.getString(2)));
                a.setBalance(rs.getLong(3));
                String str = rs.getString(4);
                if (str.equalsIgnoreCase("ACTIVE")) a.setStatus(Account.Status.ACTIVE);
                else if (str.equalsIgnoreCase("INACTIVE")) a.setStatus(Account.Status.INACTIVE);
                else a.setStatus(Account.Status.DEACTIVATED);
                accounts.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Set<Account> getAccountsLessBalanceThan(double balance) {
        Set<Account> accounts = new HashSet<>();
        String sql = "select * from accounts where balance <= " + balance + ";";

        try {
            UserDao uDao = new UserDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Account a = new Account();
                a.setAccountNumber(rs.getLong(1));
                a.setUser(uDao.getUserByUsername(rs.getString(2)));
                a.setBalance(rs.getLong(3));
                String str = rs.getString(4);
                if (str.equalsIgnoreCase("ACTIVE")) a.setStatus(Account.Status.ACTIVE);
                else if (str.equalsIgnoreCase("INACTIVE")) a.setStatus(Account.Status.INACTIVE);
                else a.setStatus(Account.Status.DEACTIVATED);
                accounts.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getAccountByNumber(long accountNumber) {
        Account a = null;
        String sql = "select * from accounts where account_number = " + accountNumber;

        try {
            UserDao uDao = new UserDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                a = new Account();
                a.setAccountNumber(rs.getLong(1));
                a.setUser(uDao.getUserByUsername(rs.getString(2)));
                a.setBalance(rs.getLong(3));
                String str = rs.getString(4);
                if (str.equalsIgnoreCase("ACTIVE")) a.setStatus(Account.Status.ACTIVE);
                else if (str.equalsIgnoreCase("INACTIVE")) a.setStatus(Account.Status.INACTIVE);
                else a.setStatus(Account.Status.DEACTIVATED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public void createAccount(Account account) {
        String sql = "insert into accounts(account_owner, balance, status) values (?, ?, ?);";

        try {
            UserDao uDao = new UserDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, account.getUser().getUsername());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getStatus().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account) {
        String sql = "update accounts set account_owner = ?, balance = ?, status = ? where account_number = ?;";

        try {
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, account.getUser().getUsername());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getStatus().toString());
            stmt.setLong(4, account.getAccountNumber());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccountByNumber(long accountNumber) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "delete from accounts where account_number = " + accountNumber + ";";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
