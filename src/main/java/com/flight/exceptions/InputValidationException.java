package com.flight.exceptions;

/**
 * CustomException class, it's thrown if ASC/DESC is not passed for sortingOrder 
 */
public class InputValidationException extends RuntimeException {

	
	private static final long serialVersionUID = -3686985594718419174L;
	
	public InputValidationException() {
		super();
	}

	

	public InputValidationException(String message) {
		super(message);
	}

}
