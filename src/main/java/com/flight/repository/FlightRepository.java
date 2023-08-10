package com.flight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flight.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	/*
	 * Calls repository method and fetch data from H2 DB
	 * @return list of Flight
	 */
	List<Flight> findByFlightOriginAndFlightDestination(String flightOrigin, String flightDestination);
}
