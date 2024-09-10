package se.sowl.roomitdomain.space.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "space_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SpaceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Integer capacity;

    @Column(name = "price_per_hour", nullable = false)
    private Double pricePerHour;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public SpaceDetail(Space space, String name, String description, Integer capacity, Double pricePerHour) {
        this.space = space;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
    }
}
