package com.revature.dao;

import com.revature.models.User;
import com.revature.utils.DAOUtilities;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    Connection connection = null;
    PreparedStatement stmt = null;

    @Override
    public Set<User> getAllUsers() {
        String sql = "select * from users";

        Set<User> users = new HashSet<>();

        try {

            connection = DAOUtilities.getConnection();

            // this will create our statement object
            Statement s = connection.createStatement();

            // We will execute the statement from above, with the SQL statement we want to execute
            ResultSet rs = s.executeQuery(sql);

            // ResultSet .next method will return true, until there are not more entries in the result set
            while (rs.next()) {
                User u = new User();
                // System.out.println(rs.getString(8));
                u.setFirstname(rs.getString(1));
                u.setLastname(rs.getString(2));
                u.setSSN(rs.getLong(3));
                u.setEmail(rs.getString(4));
                u.setUsername(rs.getString(5));
                u.setPassword(rs.getString(6));
                String type = rs.getString(7);
                if (type.equalsIgnoreCase("CUSTOMER")) u.setType(User.Type.CUSTOMER);
                else u.setType(User.Type.MANAGER);
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;

    }

    @Override
    public Set<User> getAllManagers() {
        String sql = "select * from users";

        Set<User> users = new HashSet<>();

        try {

            connection = DAOUtilities.getConnection();

            // this will create our statement object
            Statement s = connection.createStatement();

            // We will execute the statement from above, with the SQL statement we want to execute
            ResultSet rs = s.executeQuery(sql);

            // ResultSet .next method will return true, until there are not more entries in the result set
            while (rs.next()) {
                if (!rs.getString(7).equals("Manager")) continue;
                User u = new User();
                u.setFirstname(rs.getString(1));
                u.setLastname(rs.getString(2));
                u.setSSN(rs.getLong(3));
                u.setEmail(rs.getString(4));
                u.setUsername(rs.getString(5));
                u.setPassword(rs.getString(6));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        User u = null;
        String sql = "select * from users where username = '" + username + "';";

        try {
            connection = DAOUtilities.getConnection();
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                u = new User();
                u.setFirstname(rs.getString(1));
                u.setLastname(rs.getString(2));
                u.setSSN(rs.getLong(3));
                u.setEmail(rs.getString(4));
                u.setUsername(rs.getString(5));
                u.setPassword(rs.getString(6));
                String type = rs.getString(7);
                if (type.equalsIgnoreCase("manager")) u.setType(User.Type.MANAGER);
                else u.setType(User.Type.CUSTOMER);
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public User getUserBySSN(long ssn) {
        String sql = "select * from users where ssn = '" + ssn + "';";

        try {
            connection = DAOUtilities.getConnection();

            Statement s = connection.createStatement();

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                User u = new User();
                u.setFirstname(rs.getString(1));
                u.setLastname(rs.getString(2));
                u.setSSN(rs.getLong(3));
                u.setEmail(rs.getString(4));
                u.setUsername(rs.getString(5));
                u.setPassword(rs.getString(6));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createUser(User user) {

        String sql = "insert into users (first_name, last_name, ssn, email, username, password, type) values" +
                "('" + user.getFirstname() + "','" + user.getLastname() + "'," + user.getSSN() + ",'"
                + user.getEmail() + "','" + user.getUsername() + "','" + user.getPassword() + "',";
        sql += user.getType().equals(User.Type.MANAGER) ? "'Manager');" : "'Customer');";

        try {
            connection = DAOUtilities.getConnection();
            Statement s = connection.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateUser(User user) {

        String sql = "update users set first_name = ?, last_name = ?, email = ?, password = ?" +
                "where username = ?";

        try {
            connection = DAOUtilities.getConnection();
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        try {
            connection = DAOUtilities.getConnection();
            String sql = "delete from users where username=?";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
