package com.vizo.vizo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.lastName)  LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.email)     LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<User> searchUser(@Param("query") String query);
}
