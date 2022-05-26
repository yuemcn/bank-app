package com.revature.services;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.TransactionDao;
import com.revature.exceptions.*;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.utils.LoggingUtil;
import com.sun.javafx.util.Logging;

import java.util.List;

public class TransactionService {

    private TransactionDao tDao;

    public TransactionService(TransactionDao tDao) { this.tDao = tDao; }

    /**
     * Creates a new transaction
     * @param a The account the transaction belongs to
     * @param amount The amount to withdraw/deposit/transfer
     * @param desc The transaction description
     * @return The recently created transaction
     */
    public Transaction createTransaction(Account a, double amount, String desc)  throws NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException,  AccountNotFoundException {
        //check for exceptions
        checkForExceptions(a, amount);
        AccountDao aDao = new AccountDaoImpl();
        Transaction t = new Transaction(a, amount, desc);
        a.setBalance(a.getBalance() + amount);
        aDao.updateAccount(a);
        LoggingUtil.logger.info("Account #" + a.getAccountNumber() + "was updated");
        tDao.createTransaction(t);
        LoggingUtil.logger.info("New transaction for Account #" + a.getAccountNumber() + "was created");
        return t;
    }

    /**
     * Credits an account
     * @param a The account to credit
     * @param amount The amount to deposit
     */
    public Transaction credit(Account a, double amount, String desc)
            throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        if (amount <= 0) {
            LoggingUtil.logger.info("NegativeAmountException was thrown");
            throw new NegativeAmountException();
        }
        return createTransaction(a, amount, desc);
    }

    /**
     * Debits an account
     * @param a The account to debit
     * @param amount The amount to withdraw
     */
    public Transaction debit(Account a, double amount, String desc)
            throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        // check if amount is negative
        if (amount <= 0) {
            throw new NegativeAmountException();
        }
        return createTransaction(a, (amount * (-1)), desc);
    }

    /**
     * Transfers money between accounts
     * @param source The account to withdraw from
     * @param destination The account to deposit into
     * @param amount The amount being transferred
     */
    public void transferFunds(Account source, Account destination, double amount)  throws NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException,AccountNotFoundException, NegativeAmountException {
        // check if amount is negative
        String messageA = "Transfer to account " + destination.getAccountNumber();
        String messageB = "Transfer from account " + source.getAccountNumber();
        debit(source, amount, messageA);
        credit(destination, amount ,messageB);
        LoggingUtil.logger.info("Funds transferred from Account #" + source.getAccountNumber()
                + "to Account #" + destination.getAccountNumber());
    }

    /**
     * Gets all transactions from an account
     * @param accountNumber The account number
     * @return A list of all the account's transactions
     */
    public List<Transaction> getTransactionsFromAccount(long accountNumber) {
        LoggingUtil.logger.info("Successfully retrieved all transactions from Account #" + accountNumber);
        return tDao.getTransactionsByAccount(accountNumber);
    }

    /**
     * Checks for possible exceptions
     * @param a
     * @param amount
     */
    public void checkForExceptions(Account a, double amount) throws AccountNotFoundException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException {
        // check if the account exists
        if (a == null) {
            LoggingUtil.logger.info("AccountNotFoundException was thrown");
            throw new AccountNotFoundException();
        }

        // check if account amount is negative and account has a negative balance
        if (amount < 0 && a.getBalance() <=0) {
            LoggingUtil.logger.info("NegativeBalanceException was thrown");
            throw new NegativeBalanceException("Account " + a.getAccountNumber() + "has a negative balance");
        }
        // check if account is inactive
        if (a.getStatus().equals(Account.Status.INACTIVE)) {
            LoggingUtil.logger.info("InactiveAccountException was thrown");
            throw new InactiveAccountException("Account " + a.getAccountNumber() + "is inactive");
        }
        // check if account is deactivated
        if (a.getStatus().equals(Account.Status.DEACTIVATED)) {
            LoggingUtil.logger.info("DeactivatedAccountException was thrown");
            throw new DeactivatedAccountException("Account " + a.getAccountNumber() + "has been deactivated");
        }
    }

}
