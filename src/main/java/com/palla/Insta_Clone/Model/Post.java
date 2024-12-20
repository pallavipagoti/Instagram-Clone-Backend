package com.palla.Insta_Clone.Model;

import com.palla.Insta_Clone.DTO.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;

    private String image;
    private String location;
    private LocalDateTime createdAt=LocalDateTime.now();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="user_id")),
            @AttributeOverride(name="email", column=@Column(name="user_email")),
    })
    private UserDto user;

    @OneToMany
    private List<Comment> comments=new ArrayList<>();

    @Embedded
    @ElementCollection
    @JoinTable(name="likedByUsers",joinColumns = @JoinColumn(name="user_id"))
    private Set<UserDto> likedByUsers=new HashSet<>();

    @Embedded
    @ElementCollection
    @JoinTable(name="savedByUsers",joinColumns = @JoinColumn(name="user_id"))
    private Set<UserDto> savedByUsers=new HashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<UserDto> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<UserDto> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public Set<UserDto> getSavedByUsers() {
        return savedByUsers;
    }

    public void setSavedByUsers(Set<UserDto> savedByUsers) {
        this.savedByUsers = savedByUsers;
    }
}
