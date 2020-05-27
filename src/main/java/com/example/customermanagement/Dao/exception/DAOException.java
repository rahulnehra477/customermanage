package com.example.customermanagement.Dao.exception;

public class DAOException extends Exception {

    /** The serialVersionUID variable. */
    private static final long serialVersionUID = 1L;

    /**
     * Deafult constructor
     */
    public DAOException() {
    }

    /**
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
