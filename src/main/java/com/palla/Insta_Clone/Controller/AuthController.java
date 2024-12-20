package com.palla.Insta_Clone.Controller;

import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Repo.UserRepo;
import com.palla.Insta_Clone.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepo repo;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserException {
        User createdUser=service.registerUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException {
        Optional<User> opt=repo.findByEmail(auth.getName());
        if(opt.isPresent()){

            return new ResponseEntity<>(opt.get(),HttpStatus.ACCEPTED);
        }
        throw new BadCredentialsException("invalid username or password");

    }
}
