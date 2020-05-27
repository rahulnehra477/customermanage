package com.example.customermanagement.Services.exception;


public class ServiceException extends Exception {
    
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public ServiceException() {
    }

    /**
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
