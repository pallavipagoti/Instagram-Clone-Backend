package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.Exceptions.CommentException;
import com.palla.Insta_Clone.Exceptions.PostException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Comment;

public interface CommentService {

    public Comment createComment(Comment comment,Integer postId,Integer userId) throws UserException, PostException;
    public Comment findCommentById(Integer commentId) throws CommentException;
    public Comment likeComment(Integer commentId,Integer userId) throws CommentException,UserException;
    public Comment unLikeComment(Integer commentId,Integer userId) throws CommentException,UserException;

}

