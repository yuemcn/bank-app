package com.revature.dao;

import com.revature.models.User;

import java.util.Set;

public interface UserDao {

    public Set<User> getAllUsers();
    public Set<User> getAllManagers();
    public User getUserByUsername(String username);
    public User getUserBySSN(long ssn);

    public void createUser(User u);
    public void updateUser(User user);
    public void deleteUserByUsername(String username);

}
