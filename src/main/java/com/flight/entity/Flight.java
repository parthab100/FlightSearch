package com.flight.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FLIGHT")
/**
 * Entity class to access DB
 */
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int flightId;

	@Column
	private String flightNumber;

	@Column(nullable = false)
	private String flightOrigin;

	@Column(nullable = false)
	private String flightDestination;

	@Column(nullable = false)
	private double flightPrice;

	@Column
	private LocalDateTime departureTime;

	@Column
	private LocalDateTime arrivalTime;

}
