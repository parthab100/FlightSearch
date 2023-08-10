package com.flight.serviceImpl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight.constants.SortingOrder;
import com.flight.entity.Flight;
import com.flight.exceptions.FlightNotFoundException;
import com.flight.model.FlightSearchRequest;
import com.flight.model.FlightSearchResponse;
import com.flight.repository.FlightRepository;
import com.flight.service.FlightSearchService;
import com.flight.util.Util;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlightSearchServiceImpl implements FlightSearchService {

	@Autowired
	FlightRepository flightRepository;

	/**
	 * searchFlight method to get Flight details input FlightSearchRequest DTO ,
	 * Fields are flightOrigin : required, should not be empty or Null
	 * flightDestination : required,should not be empty or Null priceOrder : to sort
	 * flight details on priceOrder : optional to sort flight details on price
	 * ASC/DESC durationOrder : optional to sort flight details on duration ASC/DESC
	 * Calls repository method and manipulates data from DB
	 *
	 * @return list of FlightSearchResponse
	 */
	@Override
	public List<FlightSearchResponse> searchFlight(@NotNull FlightSearchRequest flightSearchRequest) {
		List<FlightSearchResponse> flightSearchResponseList = null;

		List<Flight> flightSearchEntityList = flightRepository.findByFlightOriginAndFlightDestination(
				flightSearchRequest.getFlightOrigin(), flightSearchRequest.getFlightDestination());

		// Convert to DTO
		if (!CollectionUtils.isEmpty(flightSearchEntityList)) {
			flightSearchResponseList = flightSearchEntityList.stream().map((flight) -> Util.convertToDto(flight))
					.collect(Collectors.toList());
		} else {
			log.error("Records Not Found for origin:{} & destination: {}", flightSearchRequest.getFlightOrigin(),
					flightSearchRequest.getFlightDestination());
			throw new FlightNotFoundException("Record Not Found for origin: " + flightSearchRequest.getFlightOrigin()
					+ " & destination:" + flightSearchRequest.getFlightDestination());
		}

		// calculate duration
		calculateDuration(flightSearchResponseList);
		// sorting on price
		if (!StringUtils.isBlank(flightSearchRequest.getPriceOrder())
				&& !StringUtils.isBlank(flightSearchRequest.getDurationOrder())) {
			flightSearchResponseList = sortFlightOnPriceDuration(flightSearchRequest, flightSearchResponseList);
		} else if (!StringUtils.isBlank(flightSearchRequest.getPriceOrder())) {
			flightSearchResponseList = sortFlightOnPrice(flightSearchRequest, flightSearchResponseList);
		}

		// sorting on duration
		else if (!StringUtils.isBlank(flightSearchRequest.getDurationOrder())) {
			flightSearchResponseList = sortFlightOnDuration(flightSearchRequest, flightSearchResponseList);
		}

		return flightSearchResponseList;
	}

	/**
	 * sortFlightOnPrice method to sort flights ASC/DESC on price
	 * 
	 * @return list of FlightSearchResponse
	 */

	private List<FlightSearchResponse> sortFlightOnPrice(FlightSearchRequest flightSearchRequest,
			List<FlightSearchResponse> flightSearchResponseDtoList) {
		List<FlightSearchResponse> sortedList = new ArrayList<>();

		if (SortingOrder.ASC.name().equalsIgnoreCase(flightSearchRequest.getPriceOrder())) {
			sortedList = flightSearchResponseDtoList.stream()
					.sorted(Comparator.comparing(FlightSearchResponse::getFlightPrice)).collect(Collectors.toList());
		} else if (SortingOrder.DESC.name().equalsIgnoreCase(flightSearchRequest.getPriceOrder())) {
			sortedList = flightSearchResponseDtoList.stream()
					.sorted(Comparator.comparing(FlightSearchResponse::getFlightPrice).reversed())
					.collect(Collectors.toList());
		}

		return sortedList;
	}

	/**
	 * sortFlightOnDuration method to sort flights ASC/DESC on time duration
	 * 
	 * @return list of FlightSearchResponse
	 */
	private List<FlightSearchResponse> sortFlightOnDuration(FlightSearchRequest flightSearchRequest,
			List<FlightSearchResponse> flightSearchResponseDtoList) {
		List<FlightSearchResponse> sortedList = new ArrayList<>();

		if (SortingOrder.ASC.name().equalsIgnoreCase(flightSearchRequest.getDurationOrder())) {
			sortedList = flightSearchResponseDtoList.stream()
					.sorted(Comparator.comparing(FlightSearchResponse::getDuration)).collect(Collectors.toList());

		} else if (SortingOrder.DESC.name().equalsIgnoreCase(flightSearchRequest.getDurationOrder())) {
			sortedList = flightSearchResponseDtoList.stream()
					.sorted(Comparator.comparing(FlightSearchResponse::getDuration).reversed())
					.collect(Collectors.toList());
		}

		return sortedList;
	}
	
	/*
	 * /**
	 * sortFlightOnPriceDuration method to sort flights ASC/DESC on both price and duration
	 * 
	 * @return list of FlightSearchResponse
	 */

	public static List<FlightSearchResponse> sortFlightOnPriceDuration(FlightSearchRequest flightSearchRequest,
			List<FlightSearchResponse> flightSearchResponseDtoList) {
		List<FlightSearchResponse> sortedList = new ArrayList<>();
		
		if (SortingOrder.DESC.name().equalsIgnoreCase(flightSearchRequest.getPriceOrder())) {
			if (SortingOrder.DESC.name().equalsIgnoreCase(flightSearchRequest.getDurationOrder())) {

				sortedList = flightSearchResponseDtoList.stream()
						.sorted(Comparator.comparing(FlightSearchResponse::getFlightPrice).reversed()
								.thenComparing(Comparator.comparing(FlightSearchResponse::getDuration).reversed()))
						.collect(Collectors.toList());
			} else if (SortingOrder.ASC.name().equalsIgnoreCase(flightSearchRequest.getDurationOrder())) {
				sortedList = flightSearchResponseDtoList.stream()
						.sorted(Comparator.comparing(FlightSearchResponse::getFlightPrice).reversed()
								.thenComparing(Comparator.comparing(FlightSearchResponse::getDuration)))
						.collect(Collectors.toList());
			}
		}
		if (SortingOrder.ASC.name().equalsIgnoreCase(flightSearchRequest.getPriceOrder())) {
			if (SortingOrder.DESC.name().equalsIgnoreCase(flightSearchRequest.getDurationOrder())) {
				sortedList = flightSearchResponseDtoList.stream()
						.sorted(Comparator.comparing(FlightSearchResponse::getFlightPrice)
								.thenComparing(Comparator.comparing(FlightSearchResponse::getDuration).reversed()))
						.collect(Collectors.toList());
			} else if (SortingOrder.ASC.name().equalsIgnoreCase(flightSearchRequest.getDurationOrder())) {
				sortedList = flightSearchResponseDtoList.stream()
						.sorted(Comparator.comparing(FlightSearchResponse::getFlightPrice)
								.thenComparing(Comparator.comparing(FlightSearchResponse::getDuration)))
						.collect(Collectors.toList());
			}
		}
		log.info("In sortFlightLstByPriceDuration :{}", sortedList);
		return sortedList;
	}

	/**
	 * calculateDuration method to calculate flight duration
	 * 
	 * @return list of FlightSearchResponse
	 */
	private List<FlightSearchResponse> calculateDuration(List<FlightSearchResponse> flightSearchResponseDtoList) {
		List<FlightSearchResponse> flightSearchResponseDurationList = new ArrayList<>();
		for (FlightSearchResponse flightSearchResponse : flightSearchResponseDtoList) {
			flightSearchResponse.setDuration(
					Duration.between(flightSearchResponse.getDepartureTime(), flightSearchResponse.getArrivalTime()));
			flightSearchResponseDurationList.add(flightSearchResponse);
		}

		return flightSearchResponseDurationList;
	}

}