package com.palla.Insta_Clone.Controller;

import com.palla.Insta_Clone.Exceptions.StoryException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Story;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Service.StoryService;
import com.palla.Insta_Clone.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    @Autowired
    StoryService service;

    @Autowired
    UserServiceImpl userService;


    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story,@RequestHeader("Authorization") String token) throws UserException {
        User user=userService.findUserProfile(token);
        Story createdStory=service.createStory(story,user.getId());
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable Integer userId) throws StoryException, UserException {
//        User user=userService.findUserById(userId);
        List<Story> stories=service.findStoryByUserId(userId);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
}
