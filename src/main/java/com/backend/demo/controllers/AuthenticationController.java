package com.backend.demo.controllers;


import com.backend.demo.models.User;
import com.backend.demo.services.AuthenticationService;
import com.backend.demo.services.UserService;
import com.backend.demo.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "login", description = "авторизирует пользователя с помощью jwt")
@CrossOrigin
public class AuthenticationController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtTokenUtil jwtTokenUtil, UserService userService, AuthenticationService authenticationService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "login existing user")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        User userFromDb = userService.loadUserByUsername(user.getEmail());

        if (authenticationService.authenticate(user, userFromDb)) {
            final String token = jwtTokenUtil.generateToken(user);
            userFromDb.setToken(token);
            userFromDb.setTokenExpirationDate(jwtTokenUtil.getExpirationDateFromToken(token));
            return ResponseEntity.ok(userFromDb);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
