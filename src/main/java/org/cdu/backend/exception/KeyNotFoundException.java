package org.cdu.backend.exception;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
