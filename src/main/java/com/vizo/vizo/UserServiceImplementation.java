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
    public User followUser(Integer reqUserId, Integer userId2) throws Exception {
        if (reqUserId.equals(userId2)) {
            throw new Exception("You cannot follow yourself");
        }

        User reqUser = findUserById(reqUserId); // follower
        User user2 = findUserById(userId2);     // being followed

        boolean alreadyFollowing = reqUser.getFollowings().stream()
                .anyMatch(u -> u.getId().equals(user2.getId()));

        if (alreadyFollowing) {
            // Unfollow
            reqUser.getFollowings().removeIf(u -> u.getId().equals(user2.getId()));
            user2.getFollowers().removeIf(u -> u.getId().equals(reqUser.getId()));
        } else {
            // Follow
            reqUser.getFollowings().add(user2);
            user2.getFollowers().add(reqUser);
        }

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
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

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;
    }
}
