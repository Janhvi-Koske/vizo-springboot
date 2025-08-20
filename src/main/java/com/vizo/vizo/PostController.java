package com.vizo.vizo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    // Create a new post
    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt,@RequestBody Post post) throws Exception {
        User reqUser=userService.findUserByJwt(jwt);
        Post createdPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Delete post
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) throws Exception {
         User reqUser=userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Find post by id
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Find all posts by user
    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) throws Exception {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Find all posts
    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPost() throws Exception {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Save post
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<Post> savedPostHandler(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) throws Exception {
         User reqUser=userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId, reqUser.getId() );
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Like post
    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) throws Exception {
         User reqUser=userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
