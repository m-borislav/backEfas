package com.backend.demo.controllers;

import com.backend.demo.models.User;
import com.backend.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/user/{id}")
    public User findUser(@PathVariable Long id){
        return userRepository.findById(id).get();
    }



}