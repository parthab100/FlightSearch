package com.flight.util;

import com.flight.entity.Flight;
import com.flight.model.FlightSearchResponse;

public class Util {

	/**
	 * convertToDto method to convert  Entity to DTO
	 * @param Flight 
	 *  @return FlightSearchResponse
	 * */
	public static FlightSearchResponse convertToDto(Flight flightSearch) {
		return FlightSearchResponse.builder()
		        .flightId(flightSearch.getFlightId())
		        .flightNumber(flightSearch.getFlightNumber())
		        .flightOrigin(flightSearch.getFlightOrigin())
		        .flightDestination(flightSearch.getFlightDestination())
		        .flightPrice(flightSearch.getFlightPrice())
		        .departureTime(flightSearch.getDepartureTime())
		        .arrivalTime(flightSearch.getArrivalTime())
		        .build();
		
	}
}
