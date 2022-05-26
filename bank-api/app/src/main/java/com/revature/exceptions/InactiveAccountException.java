package com.revature.exceptions;

public class InactiveAccountException extends Exception {

    public InactiveAccountException() { super("This account is innactive"); }

    public InactiveAccountException(String message) { super(message); }

}
