package org.aston.application.exception;

@SuppressWarnings("unused")
public class NanoSpringException extends AppException {

    public NanoSpringException(String message) {
        super(message);
    }

    public NanoSpringException(Exception cause) {
        super(cause);
    }

    public NanoSpringException(String message, Throwable cause) {
        super(message, cause);
    }

}
