package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.domain.User;
import java.util.Map;

public interface SecurityTokenGenerator {

    Map<String, String> generateToken(User user);
}
