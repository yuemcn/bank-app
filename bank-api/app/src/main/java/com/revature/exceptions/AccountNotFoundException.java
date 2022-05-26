package com.revature.exceptions;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() { super("Account does not exist"); }

    public AccountNotFoundException(String message) { super(message); }

}
