package com.palla.Insta_Clone.Controller;

import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class spinController {
    @GetMapping("/spin")
    public ResponseEntity<String> spin() {
        return new ResponseEntity<>("server started",HttpStatus.OK);
    }

}
