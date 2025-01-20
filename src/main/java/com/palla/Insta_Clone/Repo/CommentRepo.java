package com.palla.Insta_Clone.Repo;

import com.palla.Insta_Clone.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer>{
}
