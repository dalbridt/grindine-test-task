package com.grindine.testing;

import com.grindine.testing.filter.DepartedFlightFilter;
import com.grindine.testing.filter.FlightFilter;
import com.grindine.testing.filter.InconsistentDateFlightFilter;
import com.grindine.testing.filter.TransferTimeFilter;
import com.grindine.testing.model.Flight;
import com.grindine.testing.repository.FlightBuilder;
import com.grindine.testing.repository.FlightRepository;
import com.grindine.testing.service.FlightFilterService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlightRepository repository = new FlightBuilder();
        FlightFilterService filterService = new FlightFilterService(repository);

        FlightFilter depFilter = new DepartedFlightFilter(LocalDateTime.now());
        FlightFilter inconsistencyFilter = new InconsistentDateFlightFilter();
        FlightFilter transferTimeFilter = new TransferTimeFilter(2);

        List<Flight> filtred = filterService.doFilter(depFilter, inconsistencyFilter, transferTimeFilter);

        System.out.println("RESULT : ");
        for (Flight flight : filtred) {
            System.out.println(flight);
        }
    }
}
