package com.vizo.vizo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)   // ❌ remove semicolon here
    private Integer id;   // ✅ follow naming conventions (lowercase 'id')

    @ManyToOne
    private User user;

    private String image;

    private String captions;

    private LocalDateTime timestamp;
}
