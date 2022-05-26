package com.revature.models;

public class Account {

    private long accountNumber;
    private User user;
    private double balance;
    private Status status;

    // no args constructor
    public Account() {
        accountNumber = 1111111111l + (long) (Math.random() * (9999999999l - 1111111111l)); // random number
        status = Status.INACTIVE;
    }

    public Account(User user) {
        this();
        this.user = user;
    }

    // all args constructor
    public Account(long accountNumber, User user, double balance, Status status) {
        this.accountNumber = accountNumber;
        this.user = user;
        this.balance = balance;
        this.status = status;
    }

    // getters and setters

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Status enum
    public enum Status {
        ACTIVE,
        INACTIVE,
        DEACTIVATED
    }

    @Override
    public String toString() {
        return "Account #: " + accountNumber + "\n"
                + "Owner: " + user.getUsername() + "\n"
                + "Balance: " + balance + "\n"
                + "Status: " + status.toString();
    }

}
