package se.sowl.roomitdomain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sowl.roomitdomain.reservation.domain.ReservationStatus;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long> {
    ReservationStatus findByName(String name);
}
