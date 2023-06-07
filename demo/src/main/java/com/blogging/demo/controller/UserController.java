package com.blogging.demo.controller;

import com.blogging.demo.exception.InvalidCredentialsException;
import com.blogging.demo.exception.UserDoesNotExistException;
import com.blogging.demo.exception.UserExistsException;
import com.blogging.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestParam("email") String email, @RequestParam("username") String userName,
                            @RequestParam("password") String password) {
        if(StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
            try {
                userService.register(email, userName, password);
            }
            catch(UserExistsException e) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
        }
        return ResponseEntity.status(200).body("User has been successfully registered");
    }

    @PostMapping("/login")
    ResponseEntity<String> register(@RequestParam("email") String email, @RequestParam("password") String password) {
        if(StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(password)) {
            try {
                userService.login(email, password);
            }
            catch (UserDoesNotExistException e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            catch (InvalidCredentialsException e) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
        }
        return ResponseEntity.status(200).body("User has successfully logged in");
    }
}
