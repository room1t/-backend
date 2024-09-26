package se.sowl.roomitapi.reservation.exception;

public class EndTimeEarlyerThanStartTimeExeption extends RuntimeException {
    public EndTimeEarlyerThanStartTimeExeption(String message) {
        super(message);
    }
}
