package com.stackroute.accountmanager.controller;

import com.stackroute.accountmanager.domain.User;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.service.SecurityTokenGenerator;
import com.stackroute.accountmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/accountmanager")
public class UserController {


    private ResponseEntity responseEntity;
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;
    @Value("${errorString}")
    private String errorString;

    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody User user) {

        userService.saveUser(user);
        return responseEntity = new ResponseEntity(user, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody User user) throws UserNotFoundException {

        Map<String, String> map = null;

        try{
            User userObj = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if(userObj.getUsername().equals(user.getUsername())) {
                map = securityTokenGenerator.generateToken(user);
            }
            responseEntity = new ResponseEntity(map, HttpStatus.OK);
        } catch(UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch(Exception e){
            responseEntity = new ResponseEntity(errorString, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

}
