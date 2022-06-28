package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.utils.LoggingUtil;
import io.javalin.http.Handler;

import java.util.Set;

public class UserController {

    private UserService uServ;
    private ObjectMapper oMap;

    public UserController(UserService uServ) {
        this.uServ = uServ;
        this.oMap = new ObjectMapper();
    }

    public Handler handleRegisterUser = (ctx) -> {
        User u = oMap.readValue(ctx.body(), User.class);
        UserDao uDao = new UserDaoImpl();
        if ((uDao.getUserByUsername(u.getUsername()) != null)) {
            ctx.status(403);
            LoggingUtil.logger.info("Could not register user: existing user");
            throw new ExistingUserException();
        } else {
            uServ.registerUser(u.getFirstname(), u.getLastname(), u.getSSN(), u.getEmail(),
                    u.getUsername(), u.getPassword(), u.getType());
            ctx.status(201);
            ctx.result(oMap.writeValueAsString(u));
            LoggingUtil.logger.info("Successfully registered user " + u.getUsername());
        }
    };

    public Handler handleLoginUser = ctx -> {
        User u = oMap.readValue(ctx.body(), User.class);
        User login = uServ.loginUser(u.getUsername(), u.getPassword());
        ctx.status(200);
        ctx.req.getSession().setAttribute("username", login.getUsername());
        ctx.req.getSession().setAttribute("type", login.getType());
        ctx.result(oMap.writeValueAsString(login));
        LoggingUtil.logger.info("Successfully logged in user " + u.getUsername());
    };

    public Handler handleLogoutUser = ctx -> {
        ctx.req.getSession().invalidate();
        ctx.result("Logged out Successfully");
        LoggingUtil.logger.info("Successfully logged out user");
    };

    public Handler handleGetAllUsers = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to retrieve all users");
            ctx.status(401);
            ctx.result("You must be logged in to view all users");
        } else {
            User u = uServ.getUserByUsername(username);
            ctx.result(oMap.writeValueAsString(uServ.getAllUsers(u)));
            LoggingUtil.logger.info("Successfully retrieved all users");
        }
    };

    public Handler handleGetUserByUsername = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to get user info");
            ctx.status(401);
            ctx.result("You must be logged in to get user info");
        } else {
            User u = uServ.getUserByUsername(username);
            ctx.result(oMap.writeValueAsString(u));
            LoggingUtil.logger.info("Successfully retrieved info for user " + u.getUsername());
        }
    };

    public Handler handleUpdateUser = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to update profile information");
            ctx.status(401);
            ctx.result("You must be logged in to update profile information");
        } else {
            User u = oMap.readValue(ctx.body(), User.class);
            uServ.updateUser(u);
            ctx.result(oMap.writeValueAsString(u));
            ctx.status(201);
            LoggingUtil.logger.info("Successfully updated user " + username);
        }
    };

}
