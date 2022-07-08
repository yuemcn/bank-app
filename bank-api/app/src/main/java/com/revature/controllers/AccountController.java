package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.LoggingUtil;
import io.javalin.http.Handler;

import java.util.Set;


public class AccountController {

    private AccountService aServ;
    private ObjectMapper oMap;

    public AccountController(AccountService aServ) {
        this.aServ = aServ;
        this.oMap = new ObjectMapper();
    }

    public Handler handleOpenAccount = ctx -> {
        if (ctx.req.getSession().getAttribute("username") == null) {
            ctx.status(401);
            ctx.result("You must be logged in to open an account");
            LoggingUtil.logger.info("Could not create new account: not logged in");
        } else {
            UserDao uDao = new UserDaoImpl();
            String username = (String) ctx.req.getSession().getAttribute("username");
            User u = uDao.getUserByUsername(username);
            aServ.openAccount(u);
            ctx.status(201);
            ctx.result("Successfully created new account for user " + u.getUsername());
            LoggingUtil.logger.info("Successfully created new account for user " + u.getUsername());
        }
    };

    public Handler handleChangeAccountStatus = ctx -> {
        if (ctx.req.getSession().getAttribute("username") == null || !ctx.req.getSession().getAttribute("type").equals(User.Type.MANAGER)) {
            ctx.status(401);
            ctx.result("You must be logged in as a manager to change an account's status");
            LoggingUtil.logger.info("Could not change account status: not logged in as manager");
        } else {
            Account a = oMap.readValue(ctx.body(), Account.class);
            aServ.changeAccountStatus(a.getAccountNumber(), a.getStatus());
            ctx.status(200);
            ctx.result("Successfully updated Account status");
            LoggingUtil.logger.info("Successfully changed Account #" + a.getAccountNumber() + " to " + a.getStatus());
        }
    };

    public Handler handleGetAccountsByUser = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to view your accounts");
            LoggingUtil.logger.info("Could not view accounts: not logged in");
        } else {
            Set<Account> accounts = aServ.getAccountsByUser(username);
            ctx.result(oMap.writeValueAsString(accounts));
            ctx.status(200);
            LoggingUtil.logger.info("Successfully viewed accounts for user " + username);
        }
    };

    public Handler handleGetActiveAccounts = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to retrieve accounts by status");
            ctx.status(401);
            ctx.result("You must be logged in to retrieve accounts by status");
        }
        else {
            Set<Account> accounts = aServ.getAccountByStatus("ACTIVE");
            ctx.result(oMap.writeValueAsString(accounts));
            ctx.status(200);
            LoggingUtil.logger.info("Successfully retrieved all active accounts");
        }
    };

    public Handler handleGetInactiveAccounts = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to retrieve accounts by status");
            ctx.status(401);
            ctx.result("You must be logged in to retrieve accounts by status");
        }
        else {
            Set<Account> accounts = aServ.getAccountByStatus("INACTIVE");
            ctx.result(oMap.writeValueAsString(accounts));
            ctx.status(200);
            LoggingUtil.logger.info("Successfully retrieved all inactive accounts");
        }
    };

    public Handler handleGetDeactivatedAccounts = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to retrieve accounts by status");
            ctx.status(401);
            ctx.result("You must be logged in to retrieve accounts by status");
        }
        else {
            Set<Account> accounts = aServ.getAccountByStatus("DEACTIVATED");
            ctx.result(oMap.writeValueAsString(accounts));
            ctx.status(200);
            LoggingUtil.logger.info("Successfully retrieved all deactivated accounts");
        }
    };

    public Handler handleGetAllAccounts = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to view all accounts");
            ctx.status(401);
            ctx.result("You must be logged in to view all accounts");
        } else {
            UserDao uDao = new UserDaoImpl();
            UserService uServ = new UserService(uDao);
            User u = uServ.getUserByUsername(username);
            ctx.result(oMap.writeValueAsString(aServ.getAllAccounts(u)));
            LoggingUtil.logger.info("Successfully viewed all accounts");
        }
    };

    public Handler handleGetAccountDetails = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to view account details");
            ctx.status(401);
            ctx.result("You must be logged in to view account details");
        }
        else {
            long id = Long.parseLong(ctx.pathParam("id"));
            Account a = aServ.getAccountByNumber(id);
            ctx.result(oMap.writeValueAsString(a));
        }
    };

}
