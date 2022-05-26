package com.revature.exceptions;

public class NegativeAmountException extends Exception {

    public NegativeAmountException() { super("Amount cannot be negative or zero"); }

    public NegativeAmountException(String message) { super(message); }

}
