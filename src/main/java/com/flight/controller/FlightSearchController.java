package com.flight.controller;
import java.util.List;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight.model.FlightSearchRequest;
import com.flight.model.FlightSearchResponse;
import com.flight.service.FlightSearchService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
/**
 * Controller class
 */
@RequestMapping("/flightFinder")
public class FlightSearchController {
	private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);
	
	@Autowired
	private FlightSearchService flightSearchService;

	/**
	 * Get Api to fetch flight details 
	 * input FlightSearchRequest DTO , 
	 * Fields are
	 * flightOrigin : required, should not be empty or Null 
	 * flightDestination : required,should not be empty or Null priceOrder : to sort flight details on
	 * priceOrder : optional to sort flight details on price ASCE/DESC
	 * durationOrder : optional to sort flight details on duration ASCE/DESC
	 *
	 * @return list of FlightSearchResponse
	 */
	
	
	@ApiOperation(value = "Get Api to fetch flight details from Origin - Destination."
			+ " \n User can supply extra parameters in the request to sort results based on price, duration.")
	

	@GetMapping("/flights")
	public ResponseEntity<List<FlightSearchResponse>> flightSearch(@Valid 
			@RequestBody FlightSearchRequest flightSearchRequest) throws Exception {

		log.info("In FlightSearchController.flightSearch for origin:{} , destination {}",
				flightSearchRequest.getFlightOrigin(), flightSearchRequest.getFlightDestination());
		return new ResponseEntity<List<FlightSearchResponse>>(flightSearchService.searchFlight(flightSearchRequest),
				HttpStatus.OK);
	}

}
