package com.flight.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.flight.entity.Flight;

@DataJpaTest
@AutoConfigureTestDatabase
public class FlightRepositoryTest {
	@Autowired
	FlightRepository flightRepository;

	@Test
	void findByFlightOriginAndFlightDestination() {
		List<Flight> flight = flightRepository.findByFlightOriginAndFlightDestination("BOM", "DEL");

		assertThat(flight).isNotEmpty()
				.allMatch(f -> "BOM".equals(f.getFlightOrigin()) && "DEL".equals(f.getFlightDestination()));

	}
}
