package com.revature.service;

import com.revature.dao.TransactionDao;
import com.revature.exceptions.*;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionServiceTest {

    @Mock
    public static TransactionDao tDao;

    @InjectMocks
    public static TransactionService tServ;

    @Before
    public void setupBeforeMethods() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------------------------------------------------------------
    // tests for createTransaction
    // ------------------------------------------------------------------------------------------------------------

    Account a = new Account(new User());

    // test credit with positive balance, active account
    @Test
    public void testCreateTransactionCredit()
            throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setBalance(100000.0);
        a.setStatus(Account.Status.ACTIVE);
        tServ.createTransaction(a, 500, "Credit of $500");
        Assert.assertTrue(a.getBalance() == 100500);
    }

    // test credit with negative balance, active account
    @Test
    public void testCreateTransactionCreditNegativeBalance()
            throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setBalance(-7);
        a.setStatus(Account.Status.ACTIVE);
        tServ.createTransaction(a, 500, "Credit of $500");
        Assert.assertTrue(a.getBalance() == 493);
    }

    // test debit with negative balance
    @Test(expected = NegativeBalanceException.class)
    public void testCreateTransactionDebitNegativeBalance()
            throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setBalance(-7);
        a.setStatus(Account.Status.ACTIVE);
        tServ.createTransaction(a, -500, "Credit of $500");
    }

    // test debit with zero balance
    @Test(expected = NegativeBalanceException.class)
    public void testCreateTransactionDebitZeroBalance()
            throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setBalance(0);
        a.setStatus(Account.Status.ACTIVE);
        tServ.createTransaction(a, -500, "Credit of $500");
    }

    // test credit with inactive account
    @Test(expected = InactiveAccountException.class)
    public void testCreateTransactionCreditInactiveAccount()
            throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setBalance(100000);
        a.setStatus(Account.Status.INACTIVE);
        tServ.createTransaction(a, 500, "Credit of $500");
    }

    // test credit with deactivated account
    @Test(expected = DeactivatedAccountException.class)
    public void testCreateTransactionCreditDeactivatedAccount()
            throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setBalance(100000);
        a.setStatus(Account.Status.DEACTIVATED);
        tServ.createTransaction(a, 500, "Credit of $500");
    }

    // ------------------------------------------------------------------------------------------------------------
    // tests for credit
    // ------------------------------------------------------------------------------------------------------------

    // positive test
    @Test
    public void testCredit() throws NegativeAmountException, NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.ACTIVE);
        a.setBalance(100000);
        tServ.credit(a, 100, "new credit");
        Assert.assertTrue(a.getBalance() == 100100);
    }

    // test inactive account
    @Test(expected = InactiveAccountException.class)
    public void testCreditInactiveAccount() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.INACTIVE);
        tServ.credit(a, 100, "new credit");
    }

    // test deactivated account
    @Test(expected = DeactivatedAccountException.class)
    public void testCreditDeactivatedAccount() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.DEACTIVATED);
        tServ.credit(a, 100, "new credit");
    }

    // test negative amount
    @Test(expected = NegativeAmountException.class)
    public void testCreditNegativeAmount() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.ACTIVE);
        a.setBalance(100000);
        tServ.credit(a, -100, "new credit");
    }

    // ------------------------------------------------------------------------------------------------------------
    // tests for debit
    // ------------------------------------------------------------------------------------------------------------

    // positive test
    @Test
    public void testDebit() throws NegativeAmountException, NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.ACTIVE);
        a.setBalance(100000);
        tServ.debit(a,100, "new debit");
        Assert.assertTrue(a.getBalance() == 99900);
    }

    // test inactive account
    @Test(expected = InactiveAccountException.class)
    public void testDebitInactiveAccount() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.INACTIVE);
        a.setBalance(100000);
        tServ.debit(a, 100, "new debit");
    }

    // test deactivated account
    @Test(expected = DeactivatedAccountException.class)
    public void testDebitDeactivatedAccount() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.DEACTIVATED);
        a.setBalance(100000);
        tServ.debit(a, 100, "new debit");
    }

    // test negative amount
    @Test(expected = NegativeAmountException.class)
    public void testDebitNegativeAmount() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.ACTIVE);
        a.setBalance(100000);
        tServ.debit(a, -100, "new debit");
    }

    // ------------------------------------------------------------------------------------------------------------
    // tests for transferFunds
    // ------------------------------------------------------------------------------------------------------------

    Account b = new Account(new User());

    // positive test
    @Test
    public void testTransferFunds() throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException, NegativeAmountException {
        a.setBalance(100000);
        a.setStatus(Account.Status.ACTIVE);
        b.setBalance(0);
        b.setStatus(Account.Status.ACTIVE);
        tServ.transferFunds(a, b, 500);
        Assert.assertTrue(a.getBalance() == 99500);
        Assert.assertTrue(b.getBalance() == 500);
    }

    // transer negative amount
    @Test(expected = NegativeAmountException.class)
    public void testTransferFundsNegativeAmount() throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException, NegativeAmountException {
        a.setBalance(100000);
        a.setStatus(Account.Status.ACTIVE);
        b.setBalance(0);
        b.setStatus(Account.Status.ACTIVE);
        tServ.transferFunds(a, b, -500);
    }

    // Account a is inactive
    @Test(expected = InactiveAccountException.class)
    public void testTransferFundsInactiveAccountA() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.INACTIVE);
        a.setBalance(100000);
        tServ.transferFunds(a, b, 500);
    }

    // Account a is deactivated
    @Test(expected = DeactivatedAccountException.class)
    public void testTransferFundsDeactivatedAccountA() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.DEACTIVATED);
        a.setBalance(100000);
        tServ.transferFunds(a, b, 500);
    }

    // Account b is inactive
    @Test(expected = InactiveAccountException.class)
    public void testTransferFundsInactiveAccountB() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.ACTIVE);
        a.setBalance(100000);
        b.setStatus(Account.Status.INACTIVE);
        b.setBalance(100000);
        tServ.transferFunds(a, b, 500);
    }

    // Account b is deactivated
    @Test(expected = DeactivatedAccountException.class)
    public void testTransferFundsDeactivatedAccountB() throws NegativeAmountException, NegativeBalanceException,
            InactiveAccountException, DeactivatedAccountException, AccountNotFoundException {
        a.setStatus(Account.Status.ACTIVE);
        a.setBalance(100000);
        b.setStatus(Account.Status.DEACTIVATED);
        b.setBalance(100000);
        tServ.transferFunds(a, b, 500);
    }

    // Account a has a negative balance
    @Test(expected = NegativeBalanceException.class)
    public void testTransferFundsNegativeBalance() throws NegativeBalanceException, InactiveAccountException,
            DeactivatedAccountException, AccountNotFoundException, NegativeAmountException {
        a.setBalance(-500);
        a.setStatus(Account.Status.ACTIVE);
        b.setBalance(0);
        b.setStatus(Account.Status.ACTIVE);
        tServ.transferFunds(a, b, 500);
    }

}
