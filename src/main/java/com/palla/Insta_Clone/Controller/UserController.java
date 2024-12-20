package com.palla.Insta_Clone.Controller;

import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Responses.MessageResponse;
import com.palla.Insta_Clone.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserServiceImpl service;



    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Integer id) throws UserException {
        User user=service.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) throws UserException {
        User user=service.findUserByUsername(username);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUser(@RequestHeader("Authorization") String token, @PathVariable Integer followUserId) throws UserException {
        User user=service.findUserProfile(token);
        MessageResponse res=new MessageResponse(service.followUser(user.getId(),followUserId));
        return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
    }

    @PutMapping("/unfollow/{followUserId}")
    public ResponseEntity<MessageResponse> unFollowUser(@RequestHeader("Authorization") String token, @PathVariable Integer followUserId) throws UserException {
        User user=service.findUserProfile(token);
        MessageResponse res=new MessageResponse(service.unfollowUser(user.getId(),followUserId));
        return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
    }

    @GetMapping("/req")
    public ResponseEntity<User> findUserProfile(@RequestHeader("Authorization")  String token) throws UserException {

        User user=service.findUserProfile(token);

        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @GetMapping("/m/{userIds}")
    public  ResponseEntity<List<User>> findUserByUserIds(@PathVariable List<Integer> userIds) throws UserException {
        List<User> users=service.findUserByIds(userIds);
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<List<User>> searchUser(@RequestParam("q") String query) throws UserException {
        System.out.println("query is:"+query);
        List<User> users=service.searchUser(query);
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @PutMapping("/account/edit")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {
        User regUser=service.findUserProfile(token);
        User updatedUser=service.updateUserDetails(user,regUser);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);

    }





}
