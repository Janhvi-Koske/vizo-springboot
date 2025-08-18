package com.vizo.vizo;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    User findUserById(Integer userId) throws Exception;

    User findByEmail(String email);

    User followUser(Integer userId1, Integer userId2) throws Exception;

    User updateUser(User user, Integer userId) throws Exception;

    List<User> searchUser(String query);
}
