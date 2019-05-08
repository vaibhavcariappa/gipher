package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.domain.User;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private User user;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User("John","John123");
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }


    @Test
    public void testSaveUserSuccess() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User fetchedUser = userService.saveUser(user);
        Assert.assertEquals(user, fetchedUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testUserLoginSuccess() throws UserNotFoundException {
        Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        User fetchedUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        Assert.assertEquals(user, fetchedUser);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

}
