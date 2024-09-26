package se.sowl.roomitdomain.reservation.domain;

import lombok.Getter;

@Getter
public enum ReservationStatusEnum {
    RESERVED("예약 중"),
    AVAILABLE("예약 가능"),
    CLEANING("청소 중");

    private final String message;

    ReservationStatusEnum(String message) {
        this.message = message;
    }
}
