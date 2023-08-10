package com.flight.model;

import com.flight.validation.SortOrderValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 	 * FlightSearchRequest DTO class, This is used to handle incoming request 
 	 * Fields are
	 * flightOrigin : required, should not be empty or Null 
	 * flightDestination : required,should not be empty or Null priceOrder : to sort flight details on
	 * priceOrder : optional to sort flight details on price ASCE/DESC
	 * durationOrder : optional to sort flight details on duration ASCE/DESC
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequest {

	@NotBlank(message = "Invalid Origin: Empty Flight Origin")
	@NotNull(message = "Invalid Origin: Flight Origin is NULL")
	private String flightOrigin;

	@NotBlank(message = "Invalid Destination: Empty Flight Destination")
	@NotNull(message = "Invalid Destination: Flight Destination is NULL")
	private String flightDestination;

	@SortOrderValidation
	private String priceOrder;
	
	@SortOrderValidation
	private String durationOrder;


}
