package se.sowl.roomitdomain.space.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import se.sowl.roomitdomain.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "space")
@Getter
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String address;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "space", fetch = FetchType.LAZY)
    private List<SpaceDetail> spaceDetails;

    @Builder
    public Space(String name, String description, String address, Integer maxCapacity, User owner, List<SpaceDetail> spaceDetails) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.owner = owner;
        this.spaceDetails = spaceDetails;
    }

    public void addSpaceDetails(List<SpaceDetail> spaceDetails) {
        System.out.println(spaceDetails == null);
        if (this.spaceDetails == null) {
            this.spaceDetails = new ArrayList<>();
            return;
        }
        this.spaceDetails = spaceDetails;
    }
}
