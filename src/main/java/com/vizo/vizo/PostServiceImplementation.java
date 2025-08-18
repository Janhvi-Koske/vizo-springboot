package com.vizo.vizo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        User user = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);
        newPost.setCreatedAt(LocalDateTime.now());

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (!post.getUser().getId().equals(user.getId())) {
            throw new Exception("You cannot delete another user's post");
        }

        postRepository.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> opt = postRepository.findById(postId);
        if (opt.isEmpty()) {
            throw new Exception("Post not found with id: " + postId);
        }
        return opt.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        boolean alreadySaved = user.getSavedPost().stream()
                .anyMatch(p -> p.getId().equals(post.getId()));

        if (alreadySaved) {
            user.getSavedPost().removeIf(p -> p.getId().equals(post.getId()));
        } else {
            user.getSavedPost().add(post);
        }

        userRepository.save(user);
        return post;
    }

    @Override
    @Transactional
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        boolean alreadyLiked = post.getLiked().stream()
                .anyMatch(u -> u.getId().equals(user.getId()));

        if (alreadyLiked) {
            post.getLiked().removeIf(u -> u.getId().equals(user.getId()));
        } else {
            post.getLiked().add(user);
        }

        return postRepository.save(post);
    }
}
