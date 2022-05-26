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
            LoggingUtil.logger.info("ExistingUserException thrown due to reason: Duplicate username");
            throw new ExistingUserException();
        }
        if (uDao.getUserBySSN(ssn) != null) {
            LoggingUtil.logger.info("ExistingUserException thrown due to reason: Duplicate SSN");
            throw new ExistingUserException();
        }
        User u = new User(firstname, lastname, ssn, email, username, password, type);
        uDao.createUser(u);
        LoggingUtil.logger.info("Successfully created user " + username);
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
        if (uDao.getUserByUsername(username) == null || !password.equals(u.getPassword())) {
            LoggingUtil.logger.info("Attempt to login user " + username + " failed");
            throw new IncorrectUsernameOrPasswordException();
        }
        LoggingUtil.logger.info("Successfully logged in user " + username);
        return u;
    }

    /**
     * Gets all the users
     * @return a set of all the users in the database
     */
    public Set<User> getAllUsers(User u) throws UnauthorizedUserException {
        if (!u.getType().equals(User.Type.MANAGER)) {
            LoggingUtil.logger.info("Attempted to retrieve all users as a non-manager");
            throw new UnauthorizedUserException();
        }
        LoggingUtil.logger.info("Successfully retrieved all users");
        return uDao.getAllUsers();
    }

    public User getUserByUsername(String username) throws IncorrectUsernameOrPasswordException {
        User u = uDao.getUserByUsername(username);
        if (u == null) {
            LoggingUtil.logger.info("Failed attempt to find user " + username);
            throw new IncorrectUsernameOrPasswordException();
        }
        LoggingUtil.logger.info("Successfully retrieved user " + username);
        return u;
    }

}
