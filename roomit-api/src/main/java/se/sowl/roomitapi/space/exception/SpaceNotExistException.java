package se.sowl.roomitapi.space.exception;

public class SpaceNotExistException extends RuntimeException {
    public SpaceNotExistException(String message) {
        super(message);
    }
}
