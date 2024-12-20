package com.palla.Insta_Clone.Service;

import com.palla.Insta_Clone.Exceptions.StoryException;
import com.palla.Insta_Clone.Exceptions.UserException;
import com.palla.Insta_Clone.Model.Story;

import java.util.List;

public interface StoryService {
    public Story createStory(Story story,Integer userId) throws UserException;
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
}
