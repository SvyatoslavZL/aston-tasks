package org.aston.application.exception;

@SuppressWarnings("unused")
public class DaoException extends AppException {

    public DaoException(Exception cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
