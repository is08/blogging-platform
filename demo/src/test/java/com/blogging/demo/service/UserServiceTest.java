package com.blogging.demo.service;

import com.blogging.demo.entities.User;
import com.blogging.demo.exception.UserExistsException;
import com.blogging.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterANewUser() {
        String email = "abc@xyz.com";
        String userName = "abc";
        String password = "strongPassword";
        when(userRepository.existsById(email)).thenReturn(false);

        userService.register(email, userName, password);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowAnExceptionWhenRegisteredUserTriesToRegister() {
        String email = "abc@xyz.com";
        String userName = "abc";
        String password = "strongPassword";
        when(userRepository.existsById(email)).thenReturn(true);

        assertThrows(UserExistsException.class,
                () -> userService.register(email, userName, password),
                "User with this email already exists!");
    }
}
