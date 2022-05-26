package com.revature.dao;

import com.revature.models.Transaction;
import com.revature.utils.DAOUtilities;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    Connection connection = null;
    PreparedStatement stmt = null;

    /**
     * Gets all the transactions from the database
     * @return A list of all the transactions
     */
    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transactions";

        try {
            AccountDao aDao = new AccountDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setAccount(aDao.getAccountByNumber(rs.getLong(2)));
                t.setDate(rs.getDate(3).toLocalDate());
                t.setAmount(rs.getDouble(4));
                t.setDescription(rs.getString(5));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * Gets all transactions from a period
     * @param start The start date
     * @param end The end date
     * @return All transactions between the start and end dates
     */
    @Override
    public List<Transaction> getTransactionsByDate(LocalDate start, LocalDate end) {
        List<Transaction> transactions = new ArrayList<>();
        System.out.println(start.toString());
        String sql = "select * from transactions where transaction_date between '" + start.toString()
                + "' and '" + end.toString() + "';";

        try {
            AccountDao aDao = new AccountDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setAccount(aDao.getAccountByNumber(rs.getLong(2)));
                t.setDate(rs.getDate(3).toLocalDate());
                t.setAmount(rs.getDouble(4));
                t.setDescription(rs.getString(5));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    /**
     * Gets all transactions from a certain account
     * @param accountNumber The account to get transactions from
     * @return The transactions from the indicated account
     */
    @Override
    public List<Transaction> getTransactionsByAccount(long accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transactions where account_number = " + accountNumber;

        try {
            AccountDao aDao = new AccountDaoImpl();
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setAccount(aDao.getAccountByNumber(rs.getLong(2)));
                t.setDate(rs.getDate(3).toLocalDate());
                t.setAmount(rs.getDouble(4));
                t.setDescription(rs.getString(5));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    /**
     * Creates a new transaction
     * @param t Transaction to be added to the database
     */
    @Override
    public void createTransaction(Transaction t) {
        String sql = "insert into transactions (account_number, transaction_date, amount, description) values (?, ?, ?, ?)";

        try {
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, t.getAccount().getAccountNumber());
            stmt.setDate(2, Date.valueOf(t.getDate()));
            stmt.setDouble(3, t.getAmount());
            stmt.setString(4, t.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
