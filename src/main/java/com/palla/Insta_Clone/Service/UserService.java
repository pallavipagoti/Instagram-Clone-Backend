package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.User;

import java.util.List;

public interface UserService {
    public User registerUser(User user) throws UserException;
    public User findUserById(Integer userId) throws UserException;
    public User findUserProfile(String token) throws UserException;
    public User findUserByUsername(String username) throws UserException;
    public String followUser(Integer regUserId, Integer followUserId) throws UserException;
    public String unfollowUser(Integer regUserId, Integer followUserId) throws UserException;
    public List<User> findUserByIds(List<Integer> userIds) throws UserException;
    public  List<User> searchUser(String query) throws UserException;
    public User updateUserDetails(User updatedUser,User existingUser) throws UserException;

}
