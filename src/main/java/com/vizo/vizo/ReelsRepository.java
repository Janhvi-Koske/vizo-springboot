package com.vizo.vizo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsRepository extends JpaRepository<Reels, Integer>{

    public List<Reels> findByUserId(Integer userId);


}
