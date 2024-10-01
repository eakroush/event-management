package com.example.eventmanagement.services;

import com.example.eventmanagement.datamodel.User;
import com.example.eventmanagement.database.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("user1@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser("user1@example.com", "password123");

        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserByEmail() {
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByEmail("user1@example.com");

        assertNotNull(foundUser);
        verify(userRepository, times(1)).findByEmail("user1@example.com");
    }
}
