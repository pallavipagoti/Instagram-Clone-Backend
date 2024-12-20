package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.DTO.UserDto;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Repo.UserRepo;
import com.palla.Insta_Clone.Security.JwtTokenClaims;
import com.palla.Insta_Clone.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jtp;




    public User registerUser(User user) throws UserException{

        Optional<User> isEmailExist=repo.findByEmail(user.getEmail());

        if(isEmailExist.isPresent()){
            throw new UserException("Email already exists");
        }
        Optional<User> isUserNameExist=repo.findByUsername(user.getUsername());
        if(isUserNameExist.isPresent()){
            throw new UserException("Username already exists");
        }
        if(user.getEmail()==null||user.getUsername()==null||user.getPassword()==null||user.getName()==null){
            throw new UserException("All fields are mandatory");
        }
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

        return repo.save(newUser);
    }

    public User findUserById(Integer userId) throws UserException{
        Optional<User> opt=repo.findById(userId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("user not exist with id: "+userId);

    }
    public User findUserProfile(String token) throws UserException{
        token=token.substring(7);
        JwtTokenClaims jwtTokenClaims=jtp.getClaimsFromToken(token);
        String email=jwtTokenClaims.getUsername();
        Optional<User> opt=repo.findByEmail(email);
        if(opt.isPresent()){
            return opt.get();

        }
        throw new UserException("Invalid token");

    }

    public User findUserByUsername(String username) throws UserException{
        Optional<User> opt=repo.findByUsername(username);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("user not exist with id: "+username);
    }

    public String followUser(Integer regUserId, Integer followUserId) throws UserException {

        User regUser=findUserById(regUserId);
        User followUser=findUserById(followUserId);

        UserDto reg=new UserDto();
        UserDto following=new UserDto();

        reg.setId(regUser.getId());
        reg.setUsername(regUser.getUsername());
        reg.setName(regUser.getName());
        reg.setEmail(regUser.getEmail());
        reg.setUserImage(regUser.getImage());

        following.setId(followUser.getId());
        following.setUsername(followUser.getUsername());
        following.setName(followUser.getName());
        following.setEmail(followUser.getEmail());
        following.setUserImage(followUser.getImage());

        regUser.getFollowing().add(following);
        followUser.getFollowers().add(reg);
        repo.save(regUser);
        repo.save(followUser);

        return ("you are following "+followUser.getUsername());
    }

    public String unfollowUser(Integer regUserId, Integer followUserId) throws UserException {
        User regUser=findUserById(regUserId);
        User followUser=findUserById(followUserId);

        UserDto follower=new UserDto();
        UserDto following=new UserDto();

        follower.setId(regUser.getId());
        follower.setUsername(regUser.getUsername());
        follower.setName(regUser.getName());
        follower.setEmail(regUser.getEmail());
        follower.setUserImage(regUser.getImage());

        following.setId(followUser.getId());
        following.setUsername(followUser.getUsername());
        following.setName(followUser.getName());
        following.setEmail(followUser.getEmail());
        following.setUserImage(followUser.getImage());



        regUser.getFollowing().remove(following);
        followUser.getFollowers().remove(follower);
        repo.save(regUser);
        repo.save(followUser);

        return ("you have unfollowed "+followUser.getUsername());
    }

    public List<User> findUserByIds(List<Integer> userIds) throws UserException{

        List<User> users=repo.findAllUsersByUserIds(userIds);
        return users;
    }

    public  List<User> searchUser(String query) throws UserException {
        List<User> users=repo.findByUQuery(query);
        if(users.size()==0){
            throw new UserException("User not found");
        }
        return users;

    }

    public User updateUserDetails(User updatedUser,User existingUser) throws UserException {
        if(updatedUser.getEmail()!=null){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getUsername()!=null){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getBio()!=null){
            existingUser.setBio(updatedUser.getBio());
        }
        if(updatedUser.getName()!=null){
            existingUser.setName(updatedUser.getName());
        }
        if(updatedUser.getMobile()!=null){
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getGender()!=null){
            existingUser.setGender(updatedUser.getGender());
        }
        if(updatedUser.getWebsite()!=null){
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if(updatedUser.getImage()!=null){
            existingUser.setImage(updatedUser.getImage());

        }
        if(updatedUser.getId().equals(existingUser.getId())){
            return repo.save(existingUser);

        }
        throw new UserException("you cant update this user");

    }



}
