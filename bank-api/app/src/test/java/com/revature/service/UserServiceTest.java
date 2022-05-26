package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.IncorrectUsernameOrPasswordException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    public static UserDao uDao;

    @InjectMocks
    public static UserService uServ;

    @Before
    public void setupBeforeMethods() {
        MockitoAnnotations.openMocks(this);
    }

    // tests for registerUser ---------------------------------------------------------------------------------------

    @Test
    public void testRegisterUser() throws ExistingUserException {
        User testUser = uServ.registerUser("Test", "User", 554698536l,
                "tuser@test.com", "tuser", "p4ssW0rd", User.Type.CUSTOMER);

        System.out.println(testUser.toString());
    }

    @Test(expected = ExistingUserException.class)
    public void testRegisterUserDuplicateSSN() throws ExistingUserException {
        Mockito.when(uDao.getUserBySSN(222334444)).thenReturn(new User());
        User testUser = uServ.registerUser("Hailey", "McNelis", 222334444,
                "hmcnelis@gmail.com", "haileymcnelis", "password", User.Type.CUSTOMER);
        System.out.println(testUser.toString());
    }

    @Test(expected = ExistingUserException.class)
    public void testRegisterUserDuplicateUsername() throws ExistingUserException {
        Mockito.when(uDao.getUserByUsername("hmcnelis")).thenReturn(new User());
        User testUser = uServ.registerUser("Hailey", "McNelis", 546885248,
                "hmcnelis@gmail.com", "hmcnelis", "password", User.Type.CUSTOMER);
    }

    // tests for loginUser ------------------------------------------------------------------------------------------

    @Test
    public void testLoginUser() throws IncorrectUsernameOrPasswordException {
        Mockito.when(uDao.getUserByUsername(Mockito.any())).thenReturn(new User(
                "Hailey", "McNelis", 222334444, "hmcnelis@gmail.com", "hmcnelis", "password", User.Type.CUSTOMER));
        User testUser = uServ.loginUser("hmcnelis", "password");
        System.out.println(testUser.toString());
    }

    @Test(expected = IncorrectUsernameOrPasswordException.class)
    public void testLoginUserIncorrectUsername() throws IncorrectUsernameOrPasswordException {
        Mockito.when(uDao.getUserByUsername(Mockito.any())).thenReturn(null);
        User testUser = uServ.loginUser("john", "password");
        System.out.println(testUser.toString());
    }

    @Test(expected = IncorrectUsernameOrPasswordException.class)
    public void testLoginUserIncorrectPassword() throws IncorrectUsernameOrPasswordException {
        Mockito.when(uDao.getUserByUsername(Mockito.any())).thenReturn(new User(
                "Hailey", "McNelis", 222334444, "hmcnelis@gmail.com", "hmcnelis", "password", User.Type.CUSTOMER));
        User testUser = uServ.loginUser("hmcnelis", "pword");
        System.out.println(testUser.toString());
    }

}
