package se.sowl.roomitdomain.user.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "provider")
public class Provider {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(mappedBy = "provider")
    private User user;


    /*
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "provider")
    private List<User> users;
    */
    // Getters and setters
}
