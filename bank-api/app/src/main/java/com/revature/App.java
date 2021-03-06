/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.revature;

import com.revature.controllers.AccountController;
import com.revature.controllers.TransactionController;
import com.revature.controllers.UserController;
import com.revature.dao.*;
import com.revature.exceptions.*;
import com.revature.services.AccountService;
import com.revature.services.TransactionService;
import com.revature.services.UserService;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class App {

    public static UserDao uDao = new UserDaoImpl();
    public static UserService uServ = new UserService(uDao);
    public static UserController uCon = new UserController(uServ);

    public static AccountDao aDao = new AccountDaoImpl();
    public static AccountService aServ = new AccountService(aDao);
    public static AccountController aCon = new AccountController(aServ);

    public static TransactionDao tDao = new TransactionDaoImpl();
    public static TransactionService tServ = new TransactionService(tDao);
    public static TransactionController tCon = new TransactionController(tServ);

    public static void main(String[] args) {

        Javalin server = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        });

        server.exception(AccountNotFoundException.class, (exception, ctx) ->
            ctx.result("The account was not found")
        );

        server.exception(DeactivatedAccountException.class, (exception, ctx) ->
            ctx.result("Account has been deactivated")
        );

        server.exception(ExistingUserException.class, (exception, ctx) ->
            ctx.result("User already exists")
        );

        server.exception(InactiveAccountException.class, (exception, ctx) ->
            ctx.result("Account is inactive")
        );

        server.exception(IncorrectUsernameOrPasswordException.class, (exception, ctx) ->
            ctx.result("The username or password is incorrect")
        );

        server.exception(NegativeBalanceException.class, (exception, ctx) ->
            ctx.result("The account balance is negative")
        );

        server.exception(NegativeAmountException.class, (exception, ctx) ->
            ctx.result("Amount cannot be negative or zero")
        );

        server.exception(UnauthorizedUserException.class, (exception, ctx) ->
            ctx.result("Only a manager can perform these actions")
        );

        server.routes(() -> {
            path("users", () -> {
                post("/register", uCon.handleRegisterUser);
                post("/login", uCon.handleLoginUser);
                get("/logout", uCon.handleLogoutUser);
                get("/all-users", uCon.handleGetAllUsers);
                get("/{username}", uCon.handleGetUserByUsername);
                put("/edit-profile", uCon.handleUpdateUser);
            });
            path("accounts", () -> {
                post("/", aCon.handleOpenAccount);
                put("/", aCon.handleChangeAccountStatus);
                get("/", aCon.handleGetAccountsByUser);
                get("/all-accounts", aCon.handleGetAllAccounts);
                get("/details/{id}", aCon.handleGetAccountDetails);
                get("/active", aCon.handleGetActiveAccounts);
                get("/inactive", aCon.handleGetInactiveAccounts);
                get("/deactivated", aCon.handleGetDeactivatedAccounts);
            });
            path("transactions", () -> {
                post("/create",tCon.handleCreateTransaction);
                post("/credit", tCon.handleCredit);
                post("/debit", tCon.handleDebit);
                post("/transfer", tCon.handleTransferFunds);
                get("/", tCon.handleGetTransactionsFromAccount);
            });

        });

        server.before(ctx -> ctx.header("Access-Control-Allow-Credentials", "true"));
        server.before(ctx -> ctx.header("Access-Control-Expose-Headers", "*"));

        server.start(8000);

    }

}
