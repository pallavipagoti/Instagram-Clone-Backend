package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.DTO.UserDto;
import com.palla.Insta_Clone.Exceptions.PostException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Post;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Repo.PostRepo;
import com.palla.Insta_Clone.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo repo;

    @Autowired
    private UserService service;

    @Autowired
    private UserRepo userrepo;


    @Override
    public Post createPost(Post post,Integer userId) throws UserException {
        User user=service.findUserById(userId);
        UserDto userDto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getName(),user.getImage());
        post.setUser(userDto);
        Post createdPost=repo.save(post);
        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        User user=service.findUserById(userId);
        Post post=findPostById(postId);

        if(post.getUser().getId().equals(user.getId())){
            Set<UserDto> saved_users=post.getSavedByUsers();
            for(UserDto saved_user:saved_users){
                unSavedPost(postId,saved_user.getId());
            }
            repo.deleteById(post.getId());
            return "Post Deleted Successfully";
        }
        throw new PostException("You can't delete other user's post");



    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts=repo.findByUserId(userId);

        if(posts.size()==0){
            throw new UserException("this user doesn't have any posts");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer PostId) throws PostException {
        Optional<Post> opt=repo.findById(PostId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new PostException("Post not found with id "+PostId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts=repo.findAllPostByUserIds(userIds);
        if(posts.size()==0){
            throw new PostException("No post available");
        }
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=service.findUserById(userId);
        UserDto userdto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getName(),user.getImage());
        if(!user.getSavedPosts().contains(post)){
            user.getSavedPosts().add(post);
            post.getSavedByUsers().add(userdto);
            repo.save(post);
            userrepo.save(user);


        }
        return "Post Saved Successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=service.findUserById(userId);
        UserDto userdto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getName(),user.getImage());
        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
            post.getSavedByUsers().remove(userdto);
            repo.save(post);
            userrepo.save(user);

        }
        return "Post Removed Successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=service.findUserById(userId);

        UserDto userDto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getName(),user.getImage());

        post.getLikedByUsers().add(userDto);
        return repo.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=service.findUserById(userId);

        UserDto userDto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getName(),user.getImage());

        post.getLikedByUsers().remove(userDto);
        return repo.save(post);
    }
}
