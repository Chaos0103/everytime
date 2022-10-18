package project.everytime.exception;

public class BlockedUserException extends RuntimeException {
    public BlockedUserException() {
        super();
    }

    public BlockedUserException(String message) {
        super(message);
    }

    public BlockedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockedUserException(Throwable cause) {
        super(cause);
    }
}
