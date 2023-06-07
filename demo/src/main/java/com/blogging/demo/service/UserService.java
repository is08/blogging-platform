package com.blogging.demo.service;

import com.blogging.demo.entities.User;
import com.blogging.demo.exception.InvalidCredentialsException;
import com.blogging.demo.exception.UserDoesNotExistException;
import com.blogging.demo.exception.UserExistsException;
import com.blogging.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(String email, String userName, String password) {
        if(userRepository.existsById(email)) {
           throw new UserExistsException("User with this email already exists!");
        }
        User newUser = new User(email, userName, password);
        userRepository.save(newUser);
    }

    public void login(String email, String password) {
        Optional<User> optionalUser = userRepository.findById(email);

        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExistException("User does not exist. Please register first.");
        }

        User user = optionalUser.get();

        if(!password.equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials. Please check email/password");
        }
    }

}
