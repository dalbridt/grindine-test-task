package com.grindine.testing.service;

import com.grindine.testing.filter.FlightFilter;
import com.grindine.testing.model.Flight;
import com.grindine.testing.repository.FlightRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterService {
    private FlightRepository flightRepository;

    public FlightFilterService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> doFilter(FlightFilter... params) {
        List <FlightFilter> allFilters = Arrays.asList(params);
        FlightFilter assembledFilter = allFilters.stream().reduce(FlightFilter::and).orElse(fl -> true);

        List<Flight> flights = flightRepository.getFlights();

        return flights
                .stream()
                .filter(assembledFilter).collect(Collectors.toList());
    }
}
