package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.domain.User;
import com.stackroute.accountmanager.exception.UserNotFoundException;

public interface UserService {

    public User saveUser(User user);
    public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException;

}
