package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AccountServiceTest {

    @Mock
    public static AccountDao aDao;

    @InjectMocks
    public static AccountService aServ;

    @Before
    public void setupBeforeMethods() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------------------------------------------------------------
    // tests for openAccount
    // ------------------------------------------------------------------------------------------------------------

    User u = new User("Hailey", "McNelis", 222334444l, "hmcnelis@email.com", "hmcnelis", "password", User.Type.MANAGER);

    @Test
    public void testOpenAccount() {
        Account testAccount = aServ.openAccount(u);
        System.out.println(testAccount.toString());
    }

    // ------------------------------------------------------------------------------------------------------------
    // tests for changeAccountStatus
    // ------------------------------------------------------------------------------------------------------------

    @Test
    public void testChangeAccountStatusActive() throws AccountNotFoundException {
        Mockito.when(aDao.getAccountByNumber(Mockito.anyLong())).thenReturn(new Account());
        Account a = aServ.changeAccountStatus(222334444l, Account.Status.ACTIVE);
        Assert.assertTrue(a.getStatus().equals(Account.Status.ACTIVE));
    }

    @Test
    public void testChangeAccountStatusInactive() throws AccountNotFoundException {
        Account a = new Account();
        a.setStatus(Account.Status.ACTIVE);
        Mockito.when(aDao.getAccountByNumber(Mockito.anyLong())).thenReturn(a);
        aServ.changeAccountStatus(222334444l, Account.Status.INACTIVE);
        Assert.assertTrue(a.getStatus().equals(Account.Status.INACTIVE));
    }

    @Test
    public void testChangeAccountStatusDeactivated() throws AccountNotFoundException {
        Mockito.when(aDao.getAccountByNumber(Mockito.anyLong())).thenReturn(new Account());
        Account a = aServ.changeAccountStatus(222334444l, Account.Status.DEACTIVATED);
        Assert.assertTrue(a.getStatus().equals(Account.Status.DEACTIVATED));
    }

    @Test(expected = AccountNotFoundException.class)
    public void testChangeAccountStatusException() throws AccountNotFoundException {
        Mockito.when(aDao.getAccountByNumber(Mockito.anyLong())).thenReturn(null);
        aServ.changeAccountStatus(222334444l, Account.Status.DEACTIVATED);
    }


}
