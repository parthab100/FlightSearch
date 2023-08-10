package com.flight.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flight.repository.FlightRepository;
import com.flight.serviceImpl.FlightSearchServiceImpl;
import com.flight.entity.Flight;
import com.flight.model.FlightSearchRequest;
import com.flight.model.FlightSearchResponse;

@ExtendWith(MockitoExtension.class)
public class FlightSearchServiceTest {

	@Mock
	FlightRepository flightRepo;
	@InjectMocks
	FlightSearchServiceImpl flightSearchService;

	@Test
	void testSearchFlightByOriginAndDestination() {
		List<Flight> flightList = this.createFlightList();
		flightList = flightList.stream()
				.filter(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()))
				.collect(Collectors.toList());
		when(flightRepo.findByFlightOriginAndFlightDestination("BOM", "DEL")).thenReturn(flightList);
		List<FlightSearchResponse> flighSearchResponseList = this.flightSearchService
				.searchFlight(buildTestFlightRequest());
		assertThat(flighSearchResponseList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()));
		verify(this.flightRepo).findByFlightOriginAndFlightDestination("BOM", "DEL");
	}

	@Test
	void testSearchFlightByPricingOrderDesc() {
		List<Flight> flightList = this.createFlightList();
		flightList = flightList.stream()
				.filter(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()))
				.collect(Collectors.toList());
		when(flightRepo.findByFlightOriginAndFlightDestination("BOM", "DEL")).thenReturn(flightList);
		List<FlightSearchResponse> flighSearchResponseList = this.flightSearchService
				.searchFlight(buildTestFlightRequestPriceDesc());

		assertThat(flighSearchResponseList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()));
		assertThat(flighSearchResponseList)
				.isSortedAccordingTo(Comparator.comparing(FlightSearchResponse::getFlightPrice).reversed());
		verify(this.flightRepo).findByFlightOriginAndFlightDestination("BOM", "DEL");
	}

	@Test
	void testSearchFlightByPricingOrderAsc() {
		List<Flight> flightList = this.createFlightList();
		flightList = flightList.stream()
				.filter(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()))
				.collect(Collectors.toList());
		when(flightRepo.findByFlightOriginAndFlightDestination("BOM", "DEL")).thenReturn(flightList);
		List<FlightSearchResponse> flighSearchResponseList = this.flightSearchService
				.searchFlight(buildTestFlightRequestPriceAsc());

		assertThat(flighSearchResponseList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()));
		assertThat(flighSearchResponseList)
				.isSortedAccordingTo(Comparator.comparing(FlightSearchResponse::getFlightPrice));
		verify(this.flightRepo).findByFlightOriginAndFlightDestination("BOM", "DEL");
	}

	@Test
	void testSearchFlightByDurationOrderAsc() {
		List<Flight> flightList = this.createFlightList();
		flightList = flightList.stream()
				.filter(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()))
				.collect(Collectors.toList());
		when(flightRepo.findByFlightOriginAndFlightDestination("BOM", "DEL")).thenReturn(flightList);
		List<FlightSearchResponse> flighSearchResponseList = this.flightSearchService
				.searchFlight(buildTestFlightRequestTimeAsc());

		assertThat(flighSearchResponseList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()));
		assertThat(flighSearchResponseList)
				.isSortedAccordingTo(Comparator.comparing(FlightSearchResponse::getFlightDestination));
		verify(this.flightRepo).findByFlightOriginAndFlightDestination("BOM", "DEL");
	}

	@Test
	void testSearchFlightByDurationOrderDesc() {
		List<Flight> flightList = this.createFlightList();
		flightList = flightList.stream()
				.filter(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()))
				.collect(Collectors.toList());
		when(flightRepo.findByFlightOriginAndFlightDestination("BOM", "DEL")).thenReturn(flightList);
		List<FlightSearchResponse> flighSearchResponseList = this.flightSearchService
				.searchFlight(buildTestFlightRequestTimeDesc());

		assertThat(flighSearchResponseList).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()));
		assertThat(flighSearchResponseList)
				.isSortedAccordingTo(Comparator.comparing(FlightSearchResponse::getFlightDestination).reversed());
		verify(this.flightRepo).findByFlightOriginAndFlightDestination("BOM", "DEL");
	}

	private List<Flight> createFlightList() {
		List<Flight> flightList = new ArrayList<>();
		flightList.add(createFlight(1, "F101", "BOM", "DEL", 80.0, LocalDateTime.parse("2023-08-03T21:30:00"),
				LocalDateTime.parse("2023-08-03T20:30:00")));

		flightList.add(createFlight(2, "G101", "BOM", "DEL", 100.0, LocalDateTime.parse("2023-08-03T19:30:00"),
				LocalDateTime.parse("2023-08-03T18:00:00")));
		return flightList;
	}

	public static Flight createFlight(int id, String flightNumber, String flightOrigin, String flightDestination,
			double flightPrice, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		return Flight.builder().flightId(1).flightNumber(flightNumber).flightOrigin(flightOrigin)
				.flightDestination(flightDestination).flightPrice(flightPrice).departureTime(departureTime)
				.arrivalTime(arrivalTime).build();

	}

	private FlightSearchRequest buildTestFlightRequest() {
		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		flightSearchRequest.setFlightOrigin("BOM");
		flightSearchRequest.setFlightDestination("DEL");
		return flightSearchRequest;

	}

	private FlightSearchRequest buildTestFlightRequestPriceAsc() {
		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		flightSearchRequest.setFlightOrigin("BOM");
		flightSearchRequest.setFlightDestination("DEL");
		flightSearchRequest.setPriceOrder("ASC");
		return flightSearchRequest;

	}

	private FlightSearchRequest buildTestFlightRequestPriceDesc() {
		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		flightSearchRequest.setFlightOrigin("BOM");
		flightSearchRequest.setFlightDestination("DEL");
		flightSearchRequest.setPriceOrder("DESC");
		return flightSearchRequest;

	}

	private FlightSearchRequest buildTestFlightRequestTimeAsc() {
		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		flightSearchRequest.setFlightOrigin("BOM");
		flightSearchRequest.setFlightDestination("DEL");
		flightSearchRequest.setDurationOrder("ASC");
		return flightSearchRequest;

	}

	private FlightSearchRequest buildTestFlightRequestTimeDesc() {
		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		flightSearchRequest.setFlightOrigin("BOM");
		flightSearchRequest.setFlightDestination("DEL");
		flightSearchRequest.setDurationOrder("DESC");
		return flightSearchRequest;

	}
}
