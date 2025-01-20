package com.palla.Insta_Clone.Controller;

import com.palla.Insta_Clone.Exceptions.CommentException;
import com.palla.Insta_Clone.Exceptions.PostException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Comment;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Service.CommentService;
import com.palla.Insta_Clone.Service.CommentServiceImpl;
import com.palla.Insta_Clone.Service.UserService;
import com.palla.Insta_Clone.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class commentController {

    @Autowired
    private CommentServiceImpl service;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment, @RequestHeader("Authorization") String token, @PathVariable Integer postId) throws UserException, PostException {
        User user=userService.findUserProfile(token);
        return new ResponseEntity<>(service.createComment(comment,postId,user.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Comment>> findCommentByIdHandler(@PathVariable Integer id) throws CommentException {
        List<Comment> comment=service.findCommentById(id);
        return new ResponseEntity<>(comment,HttpStatus.OK);

    }

    @PutMapping("/like/{id}")
    public  ResponseEntity<Comment> likeCommentHandler(@PathVariable Integer id,@RequestHeader("Authorization") String token ) throws UserException, CommentException {
        User user=userService.findUserProfile(token);
        return new ResponseEntity<>(service.likeComment(id, user.getId()),HttpStatus.OK);
    }

    @PutMapping("/unlike/{id}")
    public  ResponseEntity<Comment> unLikeCommentHandler(@PathVariable Integer id,@RequestHeader("Authorization") String token ) throws UserException, CommentException {
        User user=userService.findUserProfile(token);
        Comment comment=service.unLikeComment(id, user.getId());
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }



}
