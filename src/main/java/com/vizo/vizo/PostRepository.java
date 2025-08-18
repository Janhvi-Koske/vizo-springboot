package com.vizo.vizo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    // Derived query by relationship field "user" -> "id"
    List<Post> findByUserId(Integer userId);
}
