package com.flight.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.entity.Flight;
import com.flight.model.FlightSearchRequest;
import com.flight.model.FlightSearchResponse;
import com.flight.repository.FlightRepository;
import com.flight.service.FlightSearchService;

@WebMvcTest(FlightSearchController.class)
public class FlightSearchControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FlightRepository flightRepo;
	@MockBean
	private FlightSearchService flightService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldReturnListOfFlight() throws Exception {
		List<FlightSearchResponse> flightSearchResponse = createFlightResponseList();
		FlightSearchRequest flightSearchRequest = buildTestFlightRequest();
		when(flightService.searchFlight(buildTestFlightRequest())).thenReturn(flightSearchResponse);
		mockMvc.perform(get("/flightFinder/flights").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(flightSearchRequest))).andExpect(status().isOk())
				.andDo(print());
	}

	
	private List<FlightSearchResponse> createFlightResponseList() {
		List<FlightSearchResponse> flightList = new ArrayList<>();
		flightList.add(createFlightResponse(1, "F101", "BOM", "DEL", 80.0, LocalDateTime.parse("2023-08-03T21:30:00"),
				LocalDateTime.parse("2023-08-03T20:30:00")));

		flightList.add(createFlightResponse(2, "G101", "BOM", "DEL", 100.0, LocalDateTime.parse("2023-08-03T19:30:00"),
				LocalDateTime.parse("2023-08-03T18:00:00")));
		return flightList;
	}

	public static FlightSearchResponse createFlightResponse(int id, String flightNumber, String flightOrigin, String flightDestination,
			double flightPrice, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		return FlightSearchResponse.builder().flightId(1).flightNumber(flightNumber).flightOrigin(flightOrigin)
				.flightDestination(flightDestination).flightPrice(flightPrice).departureTime(departureTime)
				.arrivalTime(arrivalTime).build();

	}

	private FlightSearchRequest buildTestFlightRequest() {
		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		flightSearchRequest.setFlightOrigin("BOM");
		flightSearchRequest.setFlightDestination("DEL");
		flightSearchRequest.setDurationOrder("ASC");
		flightSearchRequest.setPriceOrder("ASC");
		return flightSearchRequest;

	}

	

}
