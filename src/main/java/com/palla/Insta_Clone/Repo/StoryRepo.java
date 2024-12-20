package com.palla.Insta_Clone.Repo;

import com.palla.Insta_Clone.Model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepo extends JpaRepository<Story,Integer> {

    @Query("select s from Story s where s.user.id= :userId")
    public List<Story> findAllStoryByUserId(@Param("userId") Integer userId);
}
