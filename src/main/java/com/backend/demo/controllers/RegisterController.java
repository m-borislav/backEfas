package com.backend.demo.controllers;

import com.backend.demo.models.Role;
import com.backend.demo.models.User;
import com.backend.demo.repos.UserRepository;
import com.backend.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class RegisterController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private UserRepository userRepository;

    @Autowired

    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> register(@RequestBody User user){
        final UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        if (userDetails != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            newUser.setAccountNonLocked(true);
            newUser.setAccountNonExpired(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setEnabled(true);
            newUser.setRoles(Collections.singletonList(Role.USER));
            userRepository.save(newUser);
            return ResponseEntity.ok(newUser);
        }
    }
}
