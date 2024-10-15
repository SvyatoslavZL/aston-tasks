package org.aston.application.exception;

public class DbConnectionException extends AppException{

    public DbConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbConnectionException(Throwable cause) {
        super(cause);
    }
}
