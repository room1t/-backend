package se.sowl.roomitapi.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateReservationRequestDTO {
    private Long spaceDetailId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
