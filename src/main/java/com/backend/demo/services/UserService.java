package com.backend.demo.services;

import com.backend.demo.models.User;
import com.backend.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserId(Long id){
        return userRepository.findById(id).get();
    }

    @Override
    public User loadUserByUsername(String email){
        return userRepository.findByEmail(email);
    }
}
