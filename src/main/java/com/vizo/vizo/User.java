package com.vizo.vizo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    private String gender;

    // Users who liked posts (inverse side of Post.liked)
    @ManyToMany(mappedBy = "liked")
    private List<Post> likedPosts = new ArrayList<>();

    // Saved posts
    @ManyToMany
    @JoinTable(
        name = "saved_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> savedPost = new ArrayList<>();

    // Posts created by this user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    /**
     * FOLLOW RELATIONSHIP (self-referencing)
     * Use ONE join table: user_followings(user_id, following_id)
     * - 'followings' is the owning side
     * - 'followers' is the inverse side mapped by 'followings'
     */
    @ManyToMany
    @JoinTable(
        name = "user_followings",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> followings = new ArrayList<>();

    @ManyToMany(mappedBy = "followings")
    private List<User> followers = new ArrayList<>();

    public User() {}

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public List<Post> getLikedPosts() { return likedPosts; }
    public void setLikedPosts(List<Post> likedPosts) { this.likedPosts = likedPosts; }

    public List<Post> getSavedPost() { return savedPost; }
    public void setSavedPost(List<Post> savedPost) { this.savedPost = savedPost; }

    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) { this.posts = posts; }

    public List<User> getFollowers() { return followers; }
    public void setFollowers(List<User> followers) { this.followers = followers; }

    public List<User> getFollowings() { return followings; }
    public void setFollowings(List<User> followings) { this.followings = followings; }
}