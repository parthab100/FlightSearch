package com.flight.model;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

/**
 * FlightSearchResponse DTO class, This is used to handle outgoing response 
 * Fields are
 * flightId,flightNumber,flightOrigin,flightDestination,flightPrice,
 * departureTime,arrivalTime,and duration;
*/
public class FlightSearchResponse {

	@JsonIgnore
	private int flightId;

	private String flightNumber;

	private String flightOrigin;

	private String flightDestination;
	
	private double flightPrice;
	
	private LocalDateTime departureTime;
	
	private LocalDateTime arrivalTime;
		
	private Duration duration;
	
	

	
	

}
