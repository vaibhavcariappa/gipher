package com.stackroute.accountmanager.repository;

import com.stackroute.accountmanager.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        user = new User("John","John123");
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        user = null;
    }

    @Test
    public void testSaveUserSuccess() {
        userRepository.save(user);
        User userObj = userRepository.findById(user.getUserId()).get();
        Assert.assertEquals(user.getUsername(), userObj.getUsername());
        userRepository.delete(user);
    }

    @Test
    public void testUserLoginSuccess() {
        userRepository.save(user);
        User userObj = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        Assert.assertEquals(user.getUsername(), userObj.getUsername());
        userRepository.delete(user);
    }
}
