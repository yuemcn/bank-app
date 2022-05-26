package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;
import com.revature.models.CreateTransactionObject;
import com.revature.models.Transaction;
import com.revature.models.TransferFundsObject;
import com.revature.services.TransactionService;
import io.javalin.http.Handler;

import java.util.List;

public class TransactionController {

    private TransactionService tServ;
    private ObjectMapper oMap;

    public TransactionController(TransactionService tServ) {
        this.tServ = tServ;
        oMap = new ObjectMapper();
    }

    public Handler handleCreateTransaction = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to make a transaction.");
        } else {
            CreateTransactionObject tObject = oMap.readValue(ctx.body(), CreateTransactionObject.class);

            // make sure the username matches the username attached to the account
            AccountDao aDao = new AccountDaoImpl();
            Account a = aDao.getAccountByNumber(tObject.accountNumber);

            // make sure the account exists
            if (a == null) throw new AccountNotFoundException();

            if (!a.getUser().getUsername().equals(username)) {
                ctx.status(401);
                ctx.result("You are not authorized to make transactions for this account.");

            } else {
                Transaction t = tServ.createTransaction(a, tObject.amount, tObject.description);
                ctx.status(201);
                String message = "Successfully ";
                if (t.getAmount()> 0) message += "deposited $" + Math.abs(t.getAmount()) + " into ";
                else message += "withdrew $" + Math.abs(t.getAmount()) + " from ";
                message += "Account #" + t.getAccount().getAccountNumber() + "";
                message += " on " + t.getDate().toString() + ".";
                message += " Current balance is $" + a.getBalance() + ".";
                ctx.result(message);
            }

        }
    };

    public Handler handleCredit = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        // check if user is logged in
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to make a transaction.");
        } else {
            CreateTransactionObject tObject = oMap.readValue(ctx.body(), CreateTransactionObject.class);

            // make sure the username matches the username attached to the account
            AccountDao aDao = new AccountDaoImpl();
            Account a = aDao.getAccountByNumber(tObject.accountNumber);

            // make sure the account exists
            if (a == null) throw new AccountNotFoundException();

            if (!a.getUser().getUsername().equals(username)) {
                ctx.status(401);
                ctx.result("You are not authorized to make transactions for this account.");

            } else {
                Transaction t = tServ.credit(a, tObject.amount, tObject.description);
                ctx.status(201);
                String message = "Successfully deposited $" + Math.abs(t.getAmount())
                        + " into Account #" + t.getAccount().getAccountNumber() + " on " + t.getDate().toString()
                        + ". Current balance is $" + a.getBalance() + ".";
                ctx.result(message);
            }
        }
    };

    public Handler handleDebit = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        // check if user is logged in
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to make a transaction.");
        } else {
            CreateTransactionObject tObject = oMap.readValue(ctx.body(), CreateTransactionObject.class);

            // make sure the username matches the username attached to the account
            AccountDao aDao = new AccountDaoImpl();
            Account a = aDao.getAccountByNumber(tObject.accountNumber);

            // make sure the account exists
            if (a == null) throw new AccountNotFoundException();

            if (!a.getUser().getUsername().equals(username)) {
                ctx.status(401);
                ctx.result("You are not authorized to make transactions for this account.");

            } else {
                Transaction t = tServ.debit(a, tObject.amount, tObject.description);
                ctx.status(201);
                String message = "Successfully withdrew $" + Math.abs(t.getAmount())
                        + " from Account #" + t.getAccount().getAccountNumber() + " on " + t.getDate().toString()
                        + ". Current balance is $" + a.getBalance() + ".";
                ctx.result(message);
            }
        }
    };


    public Handler handleTransferFunds = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to make a transaction.");
        } else {

            TransferFundsObject tObject = oMap.readValue(ctx.body(), TransferFundsObject.class);

            // make sure the username matches the username attached to the account
            AccountDao aDao = new AccountDaoImpl();
            Account a = aDao.getAccountByNumber(tObject.source);
            if (a == null) throw new AccountNotFoundException();
            if (!a.getUser().getUsername().equals(username)) {
                ctx.status(401);
                ctx.result("You are not authorized to make transactions for this account.");

            } else {
                Account b = aDao.getAccountByNumber(tObject.destination);
                if (b == null) throw new AccountNotFoundException();
                tServ.transferFunds(a, b, tObject.amount);
                ctx.status(201);
                String message = "Successfully transferred $" + tObject.amount
                        + " from Account #" + tObject.source + " to Account #" + tObject.destination + ".";
                ctx.result(message);
            }
        }
    };

    public Handler handleGetTransactionsFromAccount = ctx -> {
        String username = (String) ctx.req.getSession().getAttribute("username");
        if (username == null) {
            ctx.status(401);
            ctx.result("You must be logged in to view your transaction.");
        } else {

            Account aObject = oMap.readValue(ctx.body(), Account.class);
            long accountNumber = aObject.getAccountNumber();

            // get the account
            AccountDao aDao = new AccountDaoImpl();
            Account a = aDao.getAccountByNumber(accountNumber);
            if (a == null) throw new AccountNotFoundException();

            // make sure the username matches the username attached to the account
            if (!a.getUser().getUsername().equals(username)) {
                ctx.status(401);
                ctx.result("You are not authorized to view this account's transactions");

            } else {
                List<Transaction> transactions = tServ.getTransactionsFromAccount(accountNumber);
                String result = "";
                for (Transaction t : transactions) result += t.toString() + "\n";
                ctx.status(200);
                ctx.result(result);
            }
        }
    };

}
