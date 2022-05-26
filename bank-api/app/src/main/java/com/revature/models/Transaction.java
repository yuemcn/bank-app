package com.revature.models;

import java.time.LocalDate;

public class Transaction {

    private Account account;
    private LocalDate date;
    private double amount;
    private String description;

    // no args constructor
    public Transaction(){
        date = LocalDate.now();
        description = "";
    }

    public Transaction(Account account, double amount) {
        this();
        this.account = account;
        this.amount = amount;
    }

    public Transaction(Account account, double amount, String description) {
        this(account, amount);
        this.description = description;
    }

    // all args constructor
    public Transaction(Account account, LocalDate date, double amount, String description) {
        this(account, amount, description);
        this.date = date;
    }

    // getters and setters


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return account.getAccountNumber() + "\t"
                + date.toString() + "\t"
                + amount + "\t"
                + description;
    }

}
