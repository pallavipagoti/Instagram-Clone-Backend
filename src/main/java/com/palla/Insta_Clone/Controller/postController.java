package com.palla.Insta_Clone.Controller;

import com.palla.Insta_Clone.Exceptions.PostException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Post;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Repo.UserRepo;
import com.palla.Insta_Clone.Responses.MessageResponse;
import com.palla.Insta_Clone.Service.PostServiceImpl;
import com.palla.Insta_Clone.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class postController {

    @Autowired
    private PostServiceImpl service;

    @Autowired
    private UserRepo userrepo;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post,@RequestHeader("Authorization") String token) throws UserException {
        User user=userService.findUserProfile(token);
        return new ResponseEntity<>(service.createPost(post,user.getId()), HttpStatus.CREATED);

    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable Integer userId) throws UserException {
        return new ResponseEntity<>(service.findPostByUserId(userId), HttpStatus.OK);


    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findAllPostByUserIdsHandler(@PathVariable List<Integer> ids) throws PostException, UserException {
        List<Post> posts=service.findAllPostByUserIds(ids);
        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException {
        Post post=service.findPostById(postId);

        return new ResponseEntity<>(post,HttpStatus.OK);

    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws PostException, UserException {
        User user=userService.findUserProfile(token);
        Integer userId=user.getId();
        Post post=service.likePost(postId,userId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unLikePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws PostException, UserException {
        User user=userService.findUserProfile(token);
        Integer userId=user.getId();
        Post post=service.unLikePost(postId,userId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user=userService.findUserProfile(token);
        Integer userId=user.getId();
        MessageResponse messageResponse=new MessageResponse(service.deletePost(postId,userId));
        return new ResponseEntity<>(messageResponse,HttpStatus.ACCEPTED);
    }

    @PutMapping("save_post/{postId}")
    public ResponseEntity<MessageResponse> savePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user=userService.findUserProfile(token);
        Integer userId=user.getId();
        MessageResponse messageResponse=new MessageResponse(service.savedPost(postId,userId));
        return new ResponseEntity<>(messageResponse,HttpStatus.ACCEPTED);
    }

    @PutMapping("unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unSavePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user=userService.findUserProfile(token);
        Integer userId=user.getId();
        MessageResponse messageResponse=new MessageResponse(service.unSavedPost(postId,userId));
        return new ResponseEntity<>(messageResponse,HttpStatus.ACCEPTED);
    }






}
