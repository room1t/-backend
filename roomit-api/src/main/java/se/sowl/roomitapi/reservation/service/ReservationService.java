package se.sowl.roomitapi.reservation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.sowl.roomitapi.reservation.dto.CreateReservationRequestDTO;
import se.sowl.roomitapi.reservation.exception.AlreadyReservedTimeException;
import se.sowl.roomitapi.reservation.exception.EndTimeEarlyerThanStartTimeExeption;
import se.sowl.roomitapi.reservation.exception.PastTimeReservationTimeExeption;
import se.sowl.roomitdomain.reservation.domain.Reservation;
import se.sowl.roomitdomain.reservation.domain.ReservationStatus;
import se.sowl.roomitdomain.reservation.domain.ReservationStatusEnum;
import se.sowl.roomitdomain.reservation.repository.ReservationRepository;
import se.sowl.roomitdomain.reservation.repository.ReservationStatusRepository;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationStatusRepository reservationStatusRepository;

    public Reservation createReservation(CreateReservationRequestDTO request, User user, SpaceDetail spaceDetail) {
        validate(request);
        ReservationStatus status = reservationStatusRepository.findByName("예약 중");
        Reservation reservation = new Reservation(user, spaceDetail, status, request.getStartTime(), request.getEndTime());
        return reservationRepository.save(reservation);
    }

    private void validate(CreateReservationRequestDTO request) {
        duplicateValidate(request);
        endTimeEarlierThanValidate(request);
        pastTimeReservationValidate(request);
    }

    private void duplicateValidate(CreateReservationRequestDTO request) {
        reservationRepository.findBySpaceDetailIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            request.getSpaceDetailId(),
            request.getEndTime(),
            request.getStartTime()
            ).stream().findFirst().ifPresent(reservation -> {
            throw new AlreadyReservedTimeException("이미 예약된 시간대가 포함되어 있습니다.");
        });
    }

    private void endTimeEarlierThanValidate(CreateReservationRequestDTO request) {
        LocalDateTime endTime = request.getEndTime();
        LocalDateTime startTime = request.getStartTime();
        if(endTime.isBefore(startTime)) {
            throw new EndTimeEarlyerThanStartTimeExeption("예약 종료시간이 예약 시작시간보다 이릅니다.");
        }
    }

    private void pastTimeReservationValidate(CreateReservationRequestDTO request) {
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime now = LocalDateTime.now();
        if(startTime.isBefore(now)) {
            throw new PastTimeReservationTimeExeption("예약 시간이 현재 시간보다 이릅니다.");
        }
    }
}
