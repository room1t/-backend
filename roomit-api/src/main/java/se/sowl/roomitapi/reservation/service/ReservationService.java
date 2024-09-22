package se.sowl.roomitapi.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.sowl.roomitapi.reservation.dto.CreateReservationRequestDTO;
import se.sowl.roomitapi.reservation.exception.AlreadyReservedTimeException;
import se.sowl.roomitdomain.reservation.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void createReservation(CreateReservationRequestDTO request) {
        reservationRepository.findBySpaceDetailIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            request.getSpaceDetailId(),
            request.getEndTime(),
            request.getStartTime()
        ).stream().findFirst().ifPresent(reservation -> {
            throw new AlreadyReservedTimeException("이미 예약된 시간대가 포함되어 있습니다.");
        });
    }
}
