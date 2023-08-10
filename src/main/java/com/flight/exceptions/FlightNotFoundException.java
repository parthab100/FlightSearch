package com.flight.exceptions;

/**
 * CustomException class, it's thrown if no Flights are found 
 * for a given Origin and destination 
 */
public class FlightNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = -40259370716095391L;

	public FlightNotFoundException() {
		super();
	}

	

	public FlightNotFoundException(String message) {
		super(message);
	}

	
	

}
