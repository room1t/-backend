package se.sowl.roomitdomain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sowl.roomitdomain.reservation.domain.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findBySpaceDetailIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Long spaceDetailId, LocalDateTime end, LocalDateTime start);
}
