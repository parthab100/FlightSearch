package com.flight.service;

import java.util.List;


import com.flight.model.FlightSearchRequest;
import com.flight.model.FlightSearchResponse;

import jakarta.validation.constraints.NotNull;

public interface FlightSearchService {


	public List<FlightSearchResponse>  searchFlight(@NotNull FlightSearchRequest flightSearchRequest);

}
