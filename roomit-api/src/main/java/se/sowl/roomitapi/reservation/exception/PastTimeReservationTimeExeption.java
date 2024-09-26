package se.sowl.roomitapi.reservation.exception;

public class PastTimeReservationTimeExeption extends RuntimeException {
    public PastTimeReservationTimeExeption(String message) {
        super(message);
    }
}
