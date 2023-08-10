package com.flight.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
@Slf4j
/**
 * GlobalExceptionHandler class, This is centralized class to handle differnt kinds of exceptions
 */

public class GlobalExceptionHandler {

	/**
	 * @param MethodArgumentNotValidException
	 * handleValidationErrors method got called when methodArgument is not valid
	 * @return ResponseEntity<Map<String, List<String>>>
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		log.error("Invalid method arguments :  {}", ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param FlightNotFoundException
	 * handleFlightNotFoundException exception is thrown when no flights found
	 * @return ResponseEntity<FlightErrorResponse>
	 */

	@ExceptionHandler(value = FlightNotFoundException.class)
	public ResponseEntity<FlightErrorResponse> handleFlightNotFoundException(FlightNotFoundException ex,
			WebRequest request) {
		log.error("In GlobalExceptionHandler.handleFlightNotFoundException. Exception is :  {}", ex.getMessage());
		return new ResponseEntity<>(
				createResponseBody(ex.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}
	
	/**
	 * @param InputValidationException
	 * handleFlightNotFoundException exception is thrown when for 
	 * SortingOrder anything other than ASC/DESC sent
	 * @return ResponseEntity<FlightErrorResponse>
	 */

	@ExceptionHandler(value = InputValidationException.class)
	public final ResponseEntity<FlightErrorResponse> handleBadArgumentTimeExceptions(InputValidationException ex, WebRequest request) {

		return new ResponseEntity<>(createResponseBody(ex.getMessage(), HttpStatus.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

	
	/**
	 * @param Exception
	 * Generic Exception method, to handle all kind of exception 
	 * @return ResponseEntity<FlightErrorResponse>
	 */
	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<FlightErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {

		return new ResponseEntity<>(createResponseBody(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private FlightErrorResponse createResponseBody(String message, HttpStatus httpStatus, int httpStatusCode) {
		return new FlightErrorResponse(httpStatus, httpStatusCode, LocalDateTime.now(), message);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}
}
