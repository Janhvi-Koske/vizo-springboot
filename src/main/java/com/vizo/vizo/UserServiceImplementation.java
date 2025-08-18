package com.vizo.vizo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Integer userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id " + userId));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User followUser(Integer userId1, Integer userId2) throws Exception {
        if (userId1.equals(userId2)) {
            throw new Exception("You cannot follow yourself");
        }

        User user1 = findUserById(userId1); // follower
        User user2 = findUserById(userId2); // being followed

        boolean alreadyFollowing = user1.getFollowings().stream()
                .anyMatch(u -> u.getId().equals(user2.getId()));

        if (alreadyFollowing) {
            // Unfollow toggle
            user1.getFollowings().removeIf(u -> u.getId().equals(user2.getId()));
            user2.getFollowers().removeIf(u -> u.getId().equals(user1.getId()));
        } else {
            // Follow
            user1.getFollowings().add(user2);
            user2.getFollowers().add(user1);
        }

        userRepository.save(user1);
        userRepository.save(user2);

        return user1;
    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id " + userId));

        if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        if (user.getGender() != null) existingUser.setGender(user.getGender());

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
