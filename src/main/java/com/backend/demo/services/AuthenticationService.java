package com.backend.demo.services;

import com.backend.demo.models.User;
import com.backend.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public boolean authenticate(User user, User userFromDb) {
        if (passwordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            return true;
        }
        return false;
    }

    public User updatePassword(User existingUser, String password) {
        existingUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(existingUser);
        return existingUser;
    }
}
