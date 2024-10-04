package org.cdu.backend.exception;

public class ImageSavingException extends RuntimeException {
    public ImageSavingException(String message) {
        super(message);
    }

    public ImageSavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
