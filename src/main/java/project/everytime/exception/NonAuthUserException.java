package project.everytime.exception;

public class NonAuthUserException extends RuntimeException {

    public NonAuthUserException() {
        super();
    }

    public NonAuthUserException(String message) {
        super(message);
    }

    public NonAuthUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonAuthUserException(Throwable cause) {
        super(cause);
    }
}
