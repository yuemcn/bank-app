package com.revature.models;

public class User {

    private String firstname;
    private String lastname;
    private long ssn;
    private String email;
    private String username;
    private String password;
    private Type type;

    // no args constructor
    public User() {
        firstname = "";
        lastname = "";
        email = "";
        username = "";
        password = "";
        type = Type.CUSTOMER;
    }

    // all args constructor
    public User(String firstname, String lastname, long ssn, String email, String username, String password, Type type) {
        this();
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssn = ssn;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    // getters and setters

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getSSN() {
        return ssn;
    }

    public void setSSN(long ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        CUSTOMER,
        MANAGER
    }

    @Override
    public String toString() {
        return "Name: " + firstname + " " + lastname + "\n"
                + "SSN: " + ssn + "\n"
                + "Email: " + email + "\n"
                + "Username: " + username + "\n"
                + "Password: " + password + "\n"
                + "User Type: " + type.toString();
    }

}
