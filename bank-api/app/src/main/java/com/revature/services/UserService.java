package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.exceptions.UnauthorizedUserException;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;
import com.sun.javafx.util.Logging;

import java.util.Set;

public class UserService {

    private UserDao uDao;

    public UserService(UserDao uDao) { this.uDao = uDao; }

    /**
     * Registers a user
     * @param firstname The new user's first name
     * @param lastname The new user's last name
     * @param ssn The new user's social security number
     * @param email The new user's email
     * @param username The new user's username
     * @param password The new user's password
     * @return The new user
     */
    public User registerUser(String firstname, String lastname, long ssn, String email, String username, String password, User.Type type)
            throws ExistingUserException {
        // check the database to see if the user exists with that ssn and username
        if (uDao.getUserByUsername(username) != null) {
            throw new ExistingUserException();
        }
        if (uDao.getUserBySSN(ssn) != null) {
            throw new ExistingUserException();
        }
        User u = new User(firstname, lastname, ssn, email, username, password, type);
        uDao.createUser(u);
        return u;
    }

    /**
     * Logs in a user
     * @param username The user's username
     * @param password THe user's password
     * @return the logged in user
     */
    public User loginUser(String username, String password) throws IncorrectUsernameOrPasswordException {
        User u = uDao.getUserByUsername(username);
        if (u == null || !password.equals(u.getPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
        return u;
    }

    /**
     * Gets all the users
     * @return a set of all the users in the database
     */
    public Set<User> getAllUsers(User u) throws UnauthorizedUserException {
        if (!u.getType().equals(User.Type.MANAGER)) {
            throw new UnauthorizedUserException();
        }
        return uDao.getAllUsers();
    }

    public User getUserByUsername(String username) throws IncorrectUsernameOrPasswordException {
        User u = uDao.getUserByUsername(username);
        if (u == null) {
            throw new IncorrectUsernameOrPasswordException();
        }
        return u;
    }

}
