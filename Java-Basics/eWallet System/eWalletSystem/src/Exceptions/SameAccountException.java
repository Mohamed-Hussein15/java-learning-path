package Exceptions;

public class SameAccountException extends RuntimeException {
    public SameAccountException(String message) {
        super(message);
    }
}
