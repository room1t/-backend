package se.sowl.roomitapi.reservation.exception;

public class AlreadyReservedTimeException extends RuntimeException {
    public AlreadyReservedTimeException(String message) {
        super(message);
    }
}
