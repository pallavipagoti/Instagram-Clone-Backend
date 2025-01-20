package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.DTO.UserDto;
import com.palla.Insta_Clone.Exceptions.CommentException;
import com.palla.Insta_Clone.Exceptions.PostException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Comment;
import com.palla.Insta_Clone.Model.Post;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Repo.CommentRepo;
import com.palla.Insta_Clone.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentRepo repo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserServiceImpl userService;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user=userService.findUserById(userId);
        Post post=postService.findPostById(postId);
        UserDto userdto=new UserDto();
        userdto.setId(user.getId());
        userdto.setUsername(user.getUsername());
        userdto.setEmail(user.getEmail());
        userdto.setName(user.getName());
        userdto.setUserImage(user.getImage());
        comment.setUser(userdto);
        comment.setCreatedAt(LocalDateTime.now());
        Comment createdComment=repo.save(comment);
        post.getComments().add(createdComment);
        postRepo.save(post);
        return createdComment;
    }

    @Override
    public List<Comment> findCommentById(Integer postId) throws CommentException {

        Optional<Post> post=postRepo.findById(postId);
        if(post.isPresent()){
            return post.get().getComments();
        }
        throw new CommentException("No comment with Id: "+ postId);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {

        User user= userService.findUserById(userId);
        Optional<Comment> commentopt=repo.findById(commentId);
        if(commentopt.isPresent()){
            Comment comment=commentopt.get();
            UserDto userdto=new UserDto();
            userdto.setId(user.getId());
            userdto.setUsername(user.getUsername());
            userdto.setEmail(user.getEmail());
            userdto.setName(user.getName());
            userdto.setUserImage(user.getImage());
            comment.getLikedByUsers().add(userdto);
            return repo.save(comment);
        }

        throw new CommentException("No comment with Id: "+ commentId);


    }

    @Override
    public Comment unLikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user= userService.findUserById(userId);
        Optional<Comment> commentopt=repo.findById(commentId);
        if(commentopt.isPresent()){
            Comment comment=commentopt.get();
            UserDto userdto=new UserDto();
            userdto.setId(user.getId());
            userdto.setUsername(user.getUsername());
            userdto.setEmail(user.getEmail());
            userdto.setName(user.getName());
            userdto.setUserImage(user.getImage());
            comment.getLikedByUsers().remove(userdto);
            return repo.save(comment);

        }
        throw new CommentException("No comment with Id: "+ commentId);


    }
}
