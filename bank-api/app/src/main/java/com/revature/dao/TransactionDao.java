package com.revature.dao;

import com.revature.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionDao {

    public List<Transaction> getAllTransactions();
    public List<Transaction> getTransactionsByDate(LocalDate start, LocalDate end);
    public List<Transaction> getTransactionsByAccount(long accountNumber);

    public void createTransaction(Transaction t);

}
