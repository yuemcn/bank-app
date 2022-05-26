package com.revature.exceptions;

public class DeactivatedAccountException extends Exception {

    public DeactivatedAccountException() { super("This account has been deactivated"); }

    public DeactivatedAccountException(String message) { super(message); }

}
