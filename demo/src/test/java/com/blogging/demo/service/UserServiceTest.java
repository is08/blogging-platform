package com.blogging.demo.service;

import com.blogging.demo.entities.User;
import com.blogging.demo.exception.InvalidCredentialsException;
import com.blogging.demo.exception.UserDoesNotExistException;
import com.blogging.demo.exception.UserExistsException;
import com.blogging.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @Test
    void shouldLoginWhenRegisteredUserProvidesCorrectCredentials() {
        String email = "abc@xyz.com";
        String password = "strongPassword";
        User user = mock(User.class);
        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn(password);

        assertDoesNotThrow(() ->  userService.login(email, password));
    }

    @Test
    void shouldThrowExceptionWhenUnregisteredUserTriesToLogIn() {
        String email = "abc@xyz.com";
        String password = "strongPassword";
        when(userRepository.findById(email)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () ->  userService.login(email, password));
    }

    @Test
    void shouldThrowExceptionWhenRegisteredUserTriesToLogInWithWrongCredentials() {
        String email = "abc@xyz.com";
        String password = "strongPassword";
        User user = mock(User.class);
        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn("differentPassword");

        assertThrows(InvalidCredentialsException.class, () ->  userService.login(email, password));
    }
}
