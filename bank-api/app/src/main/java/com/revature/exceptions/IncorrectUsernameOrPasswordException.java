package com.revature.exceptions;

public class IncorrectUsernameOrPasswordException extends Exception {

    public IncorrectUsernameOrPasswordException() { super("The username or password is incorrect"); }

    public IncorrectUsernameOrPasswordException(String message) { super(message); }

}
