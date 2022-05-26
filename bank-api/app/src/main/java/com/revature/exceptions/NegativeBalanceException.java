package com.revature.exceptions;

public class NegativeBalanceException extends Exception {

    public NegativeBalanceException() { super("The account balance is negative"); }

    public NegativeBalanceException(String message) { super(message); }

}
