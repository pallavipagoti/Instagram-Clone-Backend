package com.palla.Insta_Clone.Repo;

import com.palla.Insta_Clone.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

    @Query("Select p from Post p where p.user.id=?1")
    public List<Post> findByUserId(Integer userId);

    @Query("Select p from Post p where p.user.id IN :users ORDER BY p.createdAt DESC")
    public List<Post> findAllPostByUserIds(@Param("users") List<Integer> userIds);
}
