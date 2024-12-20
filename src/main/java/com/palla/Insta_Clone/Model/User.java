package com.palla.Insta_Clone.Model;

import com.palla.Insta_Clone.DTO.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;


@ToString
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String mobile;
    private String website;
    private String Bio;
    private String gender;
    private String image;
    private String password;


    public User(){}
    public User(Integer id, String name, String username, String email, String mobile, String website, String bio, String gender, String image, String password, Set<UserDto> followers, Set<UserDto> following, List<Story> stories, List<Post> savedPosts) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.website = website;
        this.Bio = bio;
        this.gender = gender;
        this.image = image;
        this.Followers = followers;
        this.Following = following;
        this.stories = stories;
        this.savedPosts = savedPosts;
        this.password=password;
    }

    @Embedded
    @ElementCollection
    private Set<UserDto> Followers=new HashSet<>();
    @Embedded
    @ElementCollection
    private Set<UserDto> Following=new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Story> stories=new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Post> savedPosts=new ArrayList<>();


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return this.Bio;
    }

    public void setBio(String bio) {
        this.Bio = bio;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserDto> getFollowers() {
        return this.Followers;
    }

    public void setFollowers(Set<UserDto> followers) {
        this.Followers = followers;
    }

    public Set<UserDto> getFollowing() {
        return this.Following;
    }

    public void setFollowing(Set<UserDto> following) {
        this.Following = following;
    }

    public List<Story> getStories() {
        return this.stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Post> getSavedPosts() {
        return this.savedPosts;
    }

    public void setSavedPosts(List<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(mobile, user.mobile) && Objects.equals(website, user.website) && Objects.equals(Bio, user.Bio) && Objects.equals(gender, user.gender) && Objects.equals(image, user.image) && Objects.equals(password, user.password) && Objects.equals(Followers, user.Followers) && Objects.equals(Following, user.Following) && Objects.equals(stories, user.stories) && Objects.equals(savedPosts, user.savedPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, mobile, website, Bio, gender, image, password, Followers, Following, stories, savedPosts);
    }
}
