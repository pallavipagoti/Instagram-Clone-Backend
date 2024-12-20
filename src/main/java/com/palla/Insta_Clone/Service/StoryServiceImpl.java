package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.DTO.UserDto;
import com.palla.Insta_Clone.Exceptions.StoryException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Story;
import com.palla.Insta_Clone.Model.User;
import com.palla.Insta_Clone.Repo.StoryRepo;
import com.palla.Insta_Clone.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService{

    @Autowired
    private StoryRepo repo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepo userRepo;


    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        User user=userService.findUserById(userId);
        UserDto userDto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getName(),user.getImage());
        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());
        user.getStories().add(story);
        userRepo.save(user);

        return repo.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {

        User user=userService.findUserById(userId);
        List<Story> stories=user.getStories();
        if(stories.size()==0){
            throw new StoryException("No stories found for user: "+user.getName());
        }
        return stories;
    }
}
