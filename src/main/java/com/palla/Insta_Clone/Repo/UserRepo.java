package com.palla.Insta_Clone.Repo;

import com.palla.Insta_Clone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);
    @Query("SELECT u From User u where u.id IN:users")
    public List<User> findAllUsersByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u where u.username Like %:query% or u.email like %:query%")
    public List<User> findByUQuery(@Param("query") String query);
}
