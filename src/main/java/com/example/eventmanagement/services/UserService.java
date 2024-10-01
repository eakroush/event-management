
package com.example.eventmanagement.services;

import com.example.eventmanagement.datamodel.User;
import com.example.eventmanagement.datamodel.Role;
import com.example.eventmanagement.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword, Role.USER);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
