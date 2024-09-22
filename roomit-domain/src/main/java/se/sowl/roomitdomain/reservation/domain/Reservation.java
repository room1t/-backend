package se.sowl.roomitdomain.reservation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "space_detail_id", nullable = false)
    private SpaceDetail spaceDetail;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ReservationStatus status;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Reservation(User user, SpaceDetail spaceDetail, ReservationStatus status, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.spaceDetail = spaceDetail;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
