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
            Account a = aServ.openAccount(u);
            ctx.status(201);
            ctx.result(oMap.writeValueAsString(a));
            LoggingUtil.logger.info("Successfully created Account #" + a.getAccountNumber());
        }
    };

    public Handler handleChangeAccountStatus = ctx -> {
        if (ctx.req.getSession().getAttribute("username") == null || !ctx.req.getSession().getAttribute("type").equals(User.Type.MANAGER)) {
            ctx.status(401);
            ctx.result("You must be logged in as a manager to change an account's status");
        } else {
            Account a = oMap.readValue(ctx.body(), Account.class);
            aServ.changeAccountStatus(a.getAccountNumber(), a.getStatus());
            ctx.status(200);
            ctx.result("Successfully updated Account status");
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
        }
    };

    public Handler handleGetAccountDetails = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            LoggingUtil.logger.info("Not logged in to view account details");
            ctx.status(401);
            ctx.result("You must be logged in to view account details");
        } else {
            Account accountObject = oMap.readValue(ctx.body(), Account.class);
            Account a = aServ.getAccountByNumber(accountObject.getAccountNumber());
            ctx.status(200);
            ctx.result(oMap.writeValueAsString(a));
        }
    };

}
