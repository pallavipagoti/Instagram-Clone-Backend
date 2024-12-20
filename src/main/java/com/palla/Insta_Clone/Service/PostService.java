package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.Exceptions.PostException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Post;

import java.util.List;

public interface PostService {

    public Post createPost(Post post,Integer userId) throws UserException;
    public String deletePost(Integer postId,Integer userId) throws UserException, PostException;
    public List<Post> findPostByUserId(Integer userId) throws UserException;
    public Post findPostById(Integer PostId) throws PostException;
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException,UserException;
    public String savedPost(Integer postId,Integer userId)throws PostException,UserException;
    public String unSavedPost(Integer postId,Integer userId)throws PostException,UserException;
    public Post likePost(Integer postId,Integer userId) throws PostException,UserException;
    public Post unLikePost(Integer postId,Integer userId) throws PostException,UserException;

}
